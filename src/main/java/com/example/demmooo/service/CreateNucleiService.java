package com.example.demmooo.service;

import com.example.demmooo.dto.ScanDTO;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.repository.ScanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateNucleiService
{
    @Autowired
    private ScanRepository scanRepository;
    public ScanEntity scanEntity;
    public void createScan(ScanDTO scanDTO)
    {
        ScanEntity scanEntity = new ScanEntity();
        scanEntity.setScan_name(scanDTO.getScan_name());
        scanEntity.setTarget(scanDTO.getTarget());
        this.scanEntity = scanEntity;
        scanRepository.save(scanEntity);
    }
}
