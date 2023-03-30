package com.example.demmooo.service;

import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.model.ScannedEntity;
import com.example.demmooo.repository.ResultRepository;
import com.example.demmooo.repository.ScannedRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class JsonPartition {
    @Autowired
    ResultRepository resultRepository;
    @Autowired
    ScannedRepository scannedRepository;
    @Autowired
    CreateNucleiService createNucleiService;
    private long vuln_id;
    private String name;
    private String type;
    private String description;
    private String severity;
    private String cwe_id;
    private String time;

    public void JsonPartition(ScanDTO scanDTO) throws InterruptedException, IOException {
        Process p1 = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c",
                "pscp -pw kali kali@192.168.1.101:/home/kali/Results1.txt C:\\Users\\qkado\\Desktop\\"});
        p1.waitFor();
        ScanEntity scanEntity = createNucleiService.scanEntity;

        File f = new File("C:\\Users\\qkado\\Desktop\\Results1.txt");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                JSONObject json = new JSONObject(line);
                //Records records = new Records();
                ResultEntity resultEntity = new ResultEntity();
                ScannedEntity scanned = new ScannedEntity();
                try {
                    name = json.getJSONObject("info").get("name").toString();
                    resultEntity.setName(json.getJSONObject("info").get("name").toString());
                    time = json.get("timestamp").toString();
                    if (time.hashCode() > 0) resultEntity.setVuln_id(time.hashCode());
                    else resultEntity.setVuln_id(time.hashCode() * -1);
                } catch (JSONException e) {
                    resultEntity.setName("null");
                }
                try {
                    resultEntity.setType(json.get("type").toString());
                } catch (JSONException e) {
                    resultEntity.setType("null");
                }

                try {
                    description = json.getJSONObject("info").get("description").toString();
                    description = description.replace("\n", "");
                    resultEntity.setDescription(description);
                } catch (JSONException e) {
                    resultEntity.setDescription("null");
                }

                try {
                    cwe_id = json.getJSONObject("info").getJSONObject("classification").getJSONArray("cwe-id").toString();
                    cwe_id = cwe_id.replace("[", "");
                    cwe_id = cwe_id.replace("]", "");
                    cwe_id = cwe_id.replace("\"", "");
                    resultEntity.setCwe_id(cwe_id);
                } catch (JSONException e) {
                    resultEntity.setCwe_id("null");
                }

                try {
                    severity = json.getJSONObject("info").get("severity").toString();
                    resultEntity.setSeverity(severity);
                } catch (JSONException e) {
                    resultEntity.setSeverity("null");
                }

                resultRepository.save(resultEntity);
                scanned.setScanEntity(scanEntity);
                scanned.setResultEntity(resultEntity);
                scannedRepository.save(scanned);
            }

        } catch (IOException io) {
            System.out.println("SFTP sunucusundan dosya okunurken istisna oluştu: " + io.getMessage());
        } catch (Exception e) {
            System.out.println("Hata oluştu. " + e.getMessage());
        }

    }
}

