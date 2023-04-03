package com.example.demmooo.service;

import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ScannedResponseDTO;
import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.model.ScannedEntity;
import com.example.demmooo.repository.ResultRepository;
import com.example.demmooo.repository.ScanRepository;
import com.example.demmooo.repository.ScannedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListResultService {

    private ScannedRepository scannedRepository;
    private ScanRepository scanRepository;
    private ResultRepository resultRepository;


    public ListResultService(ScannedRepository scannedRepository, ScanRepository scanRepository, ResultRepository resultRepository) {
        this.scannedRepository = scannedRepository;
        this.scanRepository = scanRepository;
        this.resultRepository = resultRepository;
    }


    public List<ResultDTO> resultList(long id) {
        List<ScannedEntity> scannedEntities = scannedRepository.getAllByScanEntity(id);
        List<ResultDTO> lst = new ArrayList<>();
        scannedEntities.stream().forEach(resultEntity -> {
            ResultDTO resultDTO = new ResultDTO();
            resultDTO.setId(resultEntity.getResultEntity().getId());
            resultDTO.setName(resultEntity.getResultEntity().getName());
            resultDTO.setType(resultEntity.getResultEntity().getType());
            resultDTO.setDescription(resultEntity.getResultEntity().getDescription());
            resultDTO.setSeverity(resultEntity.getResultEntity().getSeverity());
            resultDTO.setCwe_id(resultEntity.getResultEntity().getCweId());
            lst.add(resultDTO);
        });

        return lst;
    }


    public Iterable<ResultEntity> getAllResultsByScanId(Long scanId) {
        List<ScannedResponseDTO> scannedResponseDTOList = scannedRepository.findByScanEntityId(scanId).stream().map(scannedEntity ->
                new ScannedResponseDTO(scannedEntity)).collect(Collectors.toList());
        return resultRepository.findAllById(scannedResponseDTOList.stream().map(scannedResponseDTO ->
                scannedResponseDTO.getVulnId()).collect(Collectors.toList()));

    }
}
