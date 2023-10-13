package com.capstone.licencelifecyclemanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.services.CompanyService;

@RestController
@RequestMapping("/company")
public class DeviceCompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/addCompany")
    public DeviceCompany addCompany(@RequestBody DeviceCompany company)
    {
        return companyService.addCompany(company);
    }
}
