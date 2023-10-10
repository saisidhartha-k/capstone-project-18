package com.capstone.licencelifecyclemanagement.entitys;

import lombok.Data;


import jakarta.persistence.*;

import java.time.LocalDate;

@Data
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String licenseNumber;
    private int numberOfEmployees;
    private int cost;
    
    
    private String plan;
    
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private int remainingDays; 

    public Device(String name, String licenseNumber, int numberOfEmployees, int cost ,String plan,LocalDate purchaseDate,LocalDate expDate ) {
        this.name = name;
        this.licenseNumber = licenseNumber;
        this.numberOfEmployees = numberOfEmployees;
        this.plan = plan;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expDate;
        this.calculateRemainingDays();
    }

    private void calculateRemainingDays() {
        LocalDate currentDate = LocalDate.now();
        this.remainingDays = (int) currentDate.until(expiryDate).getDays();
    }


}
