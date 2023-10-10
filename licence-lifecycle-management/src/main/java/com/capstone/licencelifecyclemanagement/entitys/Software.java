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
    private String companyName;
    private String licenseNumber;
    private int numberOfEmployees;
    private int cost;

    private String plan;

    
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private Boolean isExpired;

    // public Software(String name, String licenseNumber, int numberOfEmployees, int cost, String plan, LocalDate purchaseDate, LocalDate expiryDate, Boolean isEx) {
    //     this.name = name;
    //     this.licenseNumber = licenseNumber;
    //     this.numberOfEmployees = numberOfEmployees;
    //     this.plan = plan; 
    //     this.cost = cost;
    //     this.expiryDate = expiryDate;
    //     this.purchaseDate = purchaseDate;
        
    //   }
    
    // private void calculateRemainingDays() {
    //     LocalDate currentDate = LocalDate.now();
    //     this.remainingDays = (int) currentDate.until(expiryDate).getDays();
    // }

  

}
