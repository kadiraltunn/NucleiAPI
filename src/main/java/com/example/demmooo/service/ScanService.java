package com.example.demmooo.service;

import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.repository.ScanRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ScanService
{
    private ScanRepository scanRepository;
    private ResultService resultService;


    public ScanService(ScanRepository scanRepository, ResultService resultService) {
        this.scanRepository = scanRepository;
        this.resultService = resultService;
    }


    public void createScan(ScanDTO scanDTO) throws IOException, InterruptedException {
        ScanEntity scanEntity = new ScanEntity();
        scanEntity.setScanName(scanDTO.getScanName());
        scanEntity.setTarget(scanDTO.getTarget());
        scanRepository.save(scanEntity);

        Process p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "plink kali@192.168.1.101 -pw kali -no-antispoof " +
                "\"nuclei -u " + scanDTO.getTarget() + " -json -o /home/kali/Results1.txt\""});
        p.waitFor();

        resultService.jsonPartition(scanDTO, scanEntity);
    }

}
