package com.capstone.licencelifecyclemanagement.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Device {
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
    private Boolean isExpired;
    private String location;

    @PreUpdate
    @PrePersist
    private void updateIsExpired() {
        if (expiryDate != null) {
            isExpired = LocalDate.now().isAfter(expiryDate);
        } else {
            isExpired = false; 
        }
    }
}
