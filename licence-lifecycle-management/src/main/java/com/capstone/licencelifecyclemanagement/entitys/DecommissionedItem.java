package com.capstone.licencelifecyclemanagement.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class DecommissionedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate decommissionDate = LocalDate.now();
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private String licenseNumber;
    private int purchaseId;
    private String productName;
    private ProductType productType;
}
