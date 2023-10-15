package com.capstone.licencelifecyclemanagement.entitys;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "software_id")
    private Software software;

    @ManyToOne
    @JoinColumn(name = "device_id")
    private Device device;

    private String Name;
    private LocalDate expiryDate;
    private int numberOfDaysLeft;
    private String message;
    private boolean isSoftware ;

    public void setIsSoftware(boolean isSoftware) {
        this.isSoftware = isSoftware;
    }
    
}
