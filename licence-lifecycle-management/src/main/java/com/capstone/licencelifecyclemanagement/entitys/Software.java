package com.capstone.licencelifecyclemanagement.entitys;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Software implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "company_id")
    private SoftwareCompany company;

    private String licenseNumber = String.valueOf(Math.ceil(Math.random() * 10000));
    private int numberOfEmployees;
    private int cost;
    private LocalDate purchaseDate = LocalDate.now();
    private LocalDate expiryDate;
    @Enumerated
    private Available available = Available.AVAILABLE;

}
