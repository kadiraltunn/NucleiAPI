package com.example.demmooo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ResultEntity
{
    @Id
    private  long vuln_id;

    private String name;
    private String type;
    private String description;
    private String severity;
    private String cwe_id;


}
