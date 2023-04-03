package com.example.demmooo.dto;

import com.example.demmooo.model.ScanEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScanDTO
{
    private String target;
    private String scanName;

    public ScanDTO(ScanEntity scanEntity){
        this.target = scanEntity.getTarget();
        this.scanName = scanEntity.getScanName();
    }
}
