package com.capstone.licencelifecyclemanagement.dto;

import java.time.LocalDate;

import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;

import lombok.Data;

@Data
public class SoftwareDto {
    private int cost;
    private LocalDate expiryDate;   
    private SoftwareCompany company;
}
