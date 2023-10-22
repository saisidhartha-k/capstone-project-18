package com.capstone.licencelifecyclemanagement.entitys;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class RMA implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String productName;
    private int companyId;
    private String companyName;
    private String licenseNumber;
    private String reason;
    private int cost;
    private LocalDate purchasDate;
    private LocalDate expiryDate;
    private LocalDate requestDate = LocalDate.now();
    private int numberOfEmployees;
    private String productType;

}
