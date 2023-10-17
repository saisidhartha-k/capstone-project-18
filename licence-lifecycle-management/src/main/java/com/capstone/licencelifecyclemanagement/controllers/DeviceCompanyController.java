package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.services.DeviceCompanyService;

@CrossOrigin()
@RestController
@RequestMapping("/devicecompany")
public class DeviceCompanyController {

    @Autowired
    private DeviceCompanyService deviceCompanyService;

    @PostMapping("/addCompany")
    public DeviceCompany addCompany(@RequestBody DeviceCompany company)
    {
        return deviceCompanyService.addCompany(company);
    }

    @GetMapping("/deviceCompanies")
    public List<DeviceCompany> allDeviceCompany()
    {
        return deviceCompanyService.allDeviceCompany();
    }
}
