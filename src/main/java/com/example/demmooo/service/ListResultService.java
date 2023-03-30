package com.example.demmooo.service;

import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.model.ScannedEntity;
import com.example.demmooo.repository.ResultRepository;
import com.example.demmooo.repository.ScanRepository;
import com.example.demmooo.repository.ScannedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListResultService {

    @Autowired
    private ScannedRepository scannedRepository;

    @Autowired
    private ScanRepository scanRepository;

    @Autowired
    private ResultRepository resultRepository;

    public List<ResultDTO> resultList(long id) {
        List<ScannedEntity> scannedEntities = scannedRepository.getAllByScanEntity(id);
        List<ResultDTO> lst = new ArrayList<>();
        scannedEntities.stream().forEach(resultEntity -> {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setVuln_id(resultEntity.getResultEntity().getVuln_id());
            resultDTO.setName(resultEntity.getResultEntity().getName());
            resultDTO.setType(resultEntity.getResultEntity().getType());
            resultDTO.setDescription(resultEntity.getResultEntity().getDescription());
            resultDTO.setSeverity(resultEntity.getResultEntity().getSeverity());
            resultDTO.setCwe_id(resultEntity.getResultEntity().getCwe_id());
            lst.add(resultDTO);
        });

        return lst;
    }
}
