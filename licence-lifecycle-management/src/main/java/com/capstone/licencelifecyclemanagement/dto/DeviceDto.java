package com.capstone.licencelifecyclemanagement.dto;

import java.time.LocalDate;

import com.capstone.licencelifecyclemanagement.entitys.Company;

import lombok.Data;

@Data
public class DeviceDto {
    private int cost;
    private LocalDate expiryDate;
    private String location;
    private Company company;
}
