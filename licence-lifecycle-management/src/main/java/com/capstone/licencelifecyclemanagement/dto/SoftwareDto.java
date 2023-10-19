package com.capstone.licencelifecyclemanagement.dto;

import java.time.LocalDate;

import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoftwareDto {
    private int cost;
    private LocalDate expiryDate;   
    private SoftwareCompany company;
}
