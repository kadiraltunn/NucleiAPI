package com.example.demmooo.service;

import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ScanWithResultsDTO;
import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.dto.ScannedResponseDTO;
import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.repository.ResultRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ResultService {

    private ScanService scanService;
    private ScannedService scannedService;
    private ResultRepository resultRepository;


    public ResultService(ScannedService scannedService, ResultRepository resultRepository) {
        this.scannedService = scannedService;
        this.resultRepository = resultRepository;
    }

    @Autowired
    public void setScanService(ScanService scanService) {
        this.scanService = scanService;
    }


    public ScanWithResultsDTO getScanWithResultsByScanId(Long scanId) {
        List<ScannedResponseDTO> scannedResponseDTOList = scannedService
                .findScannedEntitiesByScanId(scanId)
                .stream()
                .map(scannedEntity -> new ScannedResponseDTO(scannedEntity))
                .collect(Collectors.toList());

        Iterable<ResultEntity> vulnIds = resultRepository.findAllById(scannedResponseDTOList
                .stream()
                .map(scannedResponseDTO -> scannedResponseDTO.getVulnId())
                .collect(Collectors.toList()));

        List<ResultDTO> resultDTOList = StreamSupport.stream(vulnIds.spliterator(), false)
                .map(ResultDTO::new)
                .collect(Collectors.toList());
        return new ScanWithResultsDTO(scanService.getOneScanById(scanId), resultDTOList);
    }


    public List<ScanWithResultsDTO> getAllScanWithResults() {
        List<ScanEntity> scanEntityList = scanService.getAllScanEntities();
        return scanEntityList.stream().map(scanEntity -> getScanWithResultsByScanId(scanEntity.getId()))
                .collect(Collectors.toList());
    }


    public List<ResultDTO> getAllResults() {
        return StreamSupport.stream(resultRepository.findAll().spliterator(), false)
                .map(ResultDTO::new)
                .collect(Collectors.toList());
    }


    public boolean getExistByResultId(Long id){
        return resultRepository.existsById(id);
    }


    public void saveResults(ScanDTO scanDTO, ScanEntity scanEntity) throws InterruptedException, IOException {
        Process p1 = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c",
                "pscp -pw kali kali@192.168.1.10:/home/kali/Results.txt C:\\Users\\qkado\\Desktop\\"});
        p1.waitFor();

        File f = new File("C:\\Users\\qkado\\Desktop\\Results.txt");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line;
            while ((line = br.readLine()) != null) {

                ResultEntity resultEntity = new ResultEntity();
                JSONObject json = new JSONObject(line);

                try {
                    resultEntity.setName(json.getJSONObject("info").get("name").toString());
                } catch (JSONException e) {
                    resultEntity.setName("null");
                }

                try {
                    long id;
                    if (!json.isNull("matcher-name")) {
                        String matcherName = json.get("matcher-name").toString();
                        id = (matcherName + resultEntity.getName()).hashCode();
                    } else if (!json.isNull("extracted-results")) {
                        String extractedResults = json.getJSONArray("extracted-results").toString();
                        id = (extractedResults + resultEntity.getName()).hashCode();
                    } else {
                        id = resultEntity.getName().hashCode();
                    }
                    if (id > 0) resultEntity.setId(id);
                    else resultEntity.setId(id * -1);
                } catch (JSONException e) {
                    resultEntity.setId(resultEntity.getName().hashCode());
                }

                try {
                    if (!json.isNull("type"))
                        resultEntity.setType(json.get("type").toString());
                    else resultEntity.setType("null");
                } catch (JSONException e) {
                    resultEntity.setType("null");
                }

                try {
                    String description = json.getJSONObject("info").get("description").toString();
                    description = description.replace("\n", "");
                    resultEntity.setDescription(description);
                } catch (JSONException e) {
                    resultEntity.setDescription("null");
                }

                try {
                    String cwe_id = json.getJSONObject("info").getJSONObject("classification")
                            .getJSONArray("cwe-id").toString();
                    cwe_id = cwe_id.replace("[", "");
                    cwe_id = cwe_id.replace("]", "");
                    cwe_id = cwe_id.replace("\"", "");
                    resultEntity.setCweId(cwe_id);
                } catch (JSONException e) {
                    resultEntity.setCweId("null");
                }

                try {
                    String severity = json.getJSONObject("info").get("severity").toString();
                    resultEntity.setSeverity(severity);
                } catch (JSONException e) {
                    resultEntity.setSeverity("null");
                }

                if (!getExistByResultId(resultEntity.getId()))
                    resultRepository.save(resultEntity);
                if (!scannedService.getExistByResultIdAndScanId(resultEntity.getId(), scanEntity.getId())){
                    scannedService.saveScannedEntity(scanEntity, resultEntity);
                }
            }
            br.close();
            p1.destroy();
            deleteCaches();
        } catch (IOException io) {
            System.out.println("SFTP sunucusundan dosya okunurken istisna oluştu: " + io.getMessage());
        } catch (Exception e) {
            System.out.println("Hata oluştu. " + e.getMessage());
        }
    }


    public void deleteCaches() throws IOException, InterruptedException {
        Process p2 = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c",
                "del C:\\Users\\qkado\\Desktop\\Results.txt"});
        p2.waitFor();
    }
}

