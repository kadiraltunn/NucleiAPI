package com.example.demmooo.dto;

import com.example.demmooo.model.ScannedEntity;
import lombok.Data;

@Data
public class ScannedResponseDTO {
    private Long id;
    private Long vulnId;

    public ScannedResponseDTO(ScannedEntity scannedEntity) {
        this.id = scannedEntity.getId();
        this.vulnId = scannedEntity.getResultEntity().getId();
    }
}
