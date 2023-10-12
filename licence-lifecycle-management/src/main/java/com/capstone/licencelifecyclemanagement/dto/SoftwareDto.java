package com.capstone.licencelifecyclemanagement.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SoftwareDto {
    private int cost;
    private LocalDate expiryDate;   
}
