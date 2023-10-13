package com.capstone.licencelifecyclemanagement.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Software {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private SoftwareCompany company;
    private String licenseNumber = String.valueOf(Math.ceil(Math.random() * 10000));
    private int numberOfEmployees;
    private int cost;
    private LocalDate purchaseDate = LocalDate.now();
    private LocalDate expiryDate;
    private Boolean isExpired;

}