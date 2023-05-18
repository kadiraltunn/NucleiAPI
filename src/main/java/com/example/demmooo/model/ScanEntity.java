package com.example.demmooo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ScanEntity extends BaseEntity {
    private String target;
    private String scanName;
}
