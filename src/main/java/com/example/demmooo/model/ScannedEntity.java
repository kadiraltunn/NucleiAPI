package com.example.demmooo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ScannedEntity extends BaseEntity{

    @OneToOne()
    @JoinColumn(name = "scan_id")
    private ScanEntity scanEntity;

    @OneToOne()
    @JoinColumn(name = "vuln_id")
    private ResultEntity resultEntity;

}

