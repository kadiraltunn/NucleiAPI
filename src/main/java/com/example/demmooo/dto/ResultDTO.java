package com.example.demmooo.dto;

import com.example.demmooo.model.ResultEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO
{
    private long id;
    private String name;
    private String type;
    private String description;
    private String severity;
    private String cwe_id;

    public ResultDTO(ResultEntity resultEntity){
        this.id = resultEntity.getId();
        this.name = resultEntity.getName();
        this.type = resultEntity.getType();
        this.description = resultEntity.getDescription();
        this.severity = resultEntity.getSeverity();
        this.cwe_id = resultEntity.getCweId();
    }
}
