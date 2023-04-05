package com.example.demmooo.service;

import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.repository.ScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ScanService
{
    private ScanRepository scanRepository;
    private ResultService resultService;


    public ScanService(ScanRepository scanRepository) {
        this.scanRepository = scanRepository;
    }

    @Autowired
    public void setResultService(ResultService resultService) {
        this.resultService = resultService;
    }


    public void createScan(ScanDTO scanDTO) throws IOException, InterruptedException {
        ScanEntity scanEntity = new ScanEntity();
        scanEntity.setScanName(scanDTO.getScanName());
        scanEntity.setTarget(scanDTO.getTarget());
        scanRepository.save(scanEntity);


        Process p = Runtime.getRuntime().exec("cmd.exe /c plink kali@192.168.1.101 -pw kali -no-antispoof " +
                "nuclei -u " + scanDTO.getTarget() + " -json -o /home/kali/Results.txt");

        //İşlem kontrolü ve sonlandırma için gerekli
        BufferedReader is = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
        String line;
        while ((line = is.readLine()) != null){}

        resultService.saveResults(scanDTO, scanEntity);
    }


    public ScanEntity getOneScan(Long scanId){
        return scanRepository.findById(scanId).orElse(null);
    }


    public List<ScanDTO> getAllScans() {
        Iterable<ScanEntity> scanEntityIterable = scanRepository.findAll();
        return StreamSupport.stream(scanEntityIterable.spliterator(), false)
                .map(scanEntity -> new ScanDTO(scanEntity))
                .collect(Collectors.toList());
    }


    public List<ScanEntity> getAllScanEntity() {
        return StreamSupport.stream(scanRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}
