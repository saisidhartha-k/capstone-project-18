package com.capstone.licencelifecyclemanagement.dto;

import java.time.LocalDate;

import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDto {
    private int cost;
    private LocalDate expiryDate;
    private String location;
    private DeviceCompany company;
}
