package com.example.demmooo.service;

import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.model.ScannedEntity;
import com.example.demmooo.repository.ScannedRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScannedService {

    private ScannedRepository scannedRepository;

    public ScannedService( ScannedRepository scannedRepository) {
        this.scannedRepository = scannedRepository;
    }


    public boolean getExistByResultIdAndScanId(Long resultId, Long scanId){
        return scannedRepository.existsByResultEntityIdAndScanEntityId(resultId, scanId);
    }


    public List<ScannedEntity> findScannedEntitiesByScanId(Long id){
        return scannedRepository.findByScanEntityId(id);
    }


    public void saveScannedEntity(ScanEntity scanEntity, ResultEntity resultEntity){
        ScannedEntity scannedEntity = new ScannedEntity();
        scannedEntity.setScanEntity(scanEntity);
        scannedEntity.setResultEntity(resultEntity);
        scannedRepository.save(scannedEntity);
    }
}
