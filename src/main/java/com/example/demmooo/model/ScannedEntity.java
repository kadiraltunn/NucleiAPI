package com.example.demmooo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class ScannedEntity extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scan_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ScanEntity scanEntity;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vuln_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ResultEntity resultEntity;
}

