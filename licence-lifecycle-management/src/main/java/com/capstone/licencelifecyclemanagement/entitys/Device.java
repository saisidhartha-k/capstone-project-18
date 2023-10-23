package com.capstone.licencelifecyclemanagement.entitys;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

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
    private String licenseNumber = String.valueOf(Math.ceil(Math.random() * 10000));
    private int numberOfEmployees;
    private int cost;
    private LocalDate purchaseDate = LocalDate.now();
    private LocalDate expiryDate;
    private String location;
    @Enumerated
    private Available available = Available.AVAILABLE;

}
