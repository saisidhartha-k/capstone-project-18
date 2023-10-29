package com.capstone.licencelifecyclemanagement.entitys;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;

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

    private String licenseNumber = RandomStringUtils.randomAlphanumeric(10);
    private int numberOfEmployees;
    private int cost;
    private LocalDate purchaseDate = LocalDate.now();
    private LocalDate expiryDate;
    @Enumerated
    private Available available = Available.AVAILABLE;

}
