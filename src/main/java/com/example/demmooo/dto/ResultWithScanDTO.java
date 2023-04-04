package com.example.demmooo.dto;

import com.example.demmooo.model.ScanEntity;
import lombok.Data;

import java.util.List;

@Data
public class ResultWithScanDTO {

    private Long id;
    private String scanName;
    private String target;
    private List<ResultDTO> resultDTOList;

    public ResultWithScanDTO(ScanEntity scanEntity, List<ResultDTO> resultDTOList){
        this.id = scanEntity.getId();
        this.scanName = scanEntity.getScanName();
        this.target = scanEntity.getTarget();
        this.resultDTOList =resultDTOList;
    }
}
