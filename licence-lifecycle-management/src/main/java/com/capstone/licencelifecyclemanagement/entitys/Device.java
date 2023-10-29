package com.capstone.licencelifecyclemanagement.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Device implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private DeviceCompany company;
    private String licenseNumber = RandomStringUtils.randomAlphanumeric(10);
    private int numberOfEmployees;
    private int cost;
    private LocalDate purchaseDate = LocalDate.now();
    private LocalDate expiryDate;
    private String location;
    @Enumerated
    private Available available = Available.AVAILABLE;

}
