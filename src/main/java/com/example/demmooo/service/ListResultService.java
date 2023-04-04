package com.example.demmooo.service;

import com.example.demmooo.dto.ResultDTO;
import com.example.demmooo.dto.ResultWithScanDTO;
import com.example.demmooo.dto.ScannedResponseDTO;
import com.example.demmooo.model.ResultEntity;
import com.example.demmooo.model.ScanEntity;
import com.example.demmooo.model.ScannedEntity;
import com.example.demmooo.repository.ResultRepository;
import com.example.demmooo.repository.ScannedRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ListResultService {

    private ScanService scanService;
    private ScannedRepository scannedRepository;
    private ResultRepository resultRepository;


    public ListResultService(ScanService scanService, ScannedRepository scannedRepository, ResultRepository resultRepository) {
        this.scanService = scanService;
        this.scannedRepository = scannedRepository;
        this.resultRepository = resultRepository;
    }


    public ResultWithScanDTO getAllResultsByScanId(Long scanId) {
        List<ScannedResponseDTO> scannedResponseDTOList = scannedRepository.findByScanEntityId(scanId).stream()
                .map(scannedEntity -> new ScannedResponseDTO(scannedEntity))
                .collect(Collectors.toList());

        Iterable<ResultEntity> vulnIds = resultRepository.findAllById(scannedResponseDTOList.stream()
                .map(scannedResponseDTO -> scannedResponseDTO.getVulnId())
                .collect(Collectors.toList()));

        List<ResultDTO> resultDTOList = StreamSupport.stream(vulnIds.spliterator(), false)
                .map(ResultDTO::new)
                .collect(Collectors.toList());
        return new ResultWithScanDTO(scanService.getOneScan(scanId), resultDTOList);
    }


    public List<ResultWithScanDTO> getAllResultsWithAllScansById() {
        List<ScanEntity> scanEntityList = scanService.getAllScanEntity();
        return scanEntityList.stream().map(scanEntity -> getAllResultsByScanId(scanEntity.getId())).collect(Collectors.toList());
    }


    public List<ResultDTO> getAllResults() {
        return StreamSupport.stream(resultRepository.findAll().spliterator(), false)
                .map(ResultDTO::new)
                .collect(Collectors.toList());
    }
}
