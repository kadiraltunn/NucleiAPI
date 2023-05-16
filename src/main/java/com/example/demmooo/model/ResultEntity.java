package com.example.demmooo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ResultEntity {

    @Id
    private long id;
    private String name;
    private String type;

    @Column(columnDefinition = "text")
    private String description;
    private String severity;
    private String cweId;

}
