package com.example.demmooo.dto;

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


}
