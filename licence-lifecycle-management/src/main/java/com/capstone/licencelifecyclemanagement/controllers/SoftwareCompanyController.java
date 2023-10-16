package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.services.SoftwareService;

@CrossOrigin()
@RestController
@RequestMapping("/softwarecompany")
public class SoftwareCompanyController {

    @Autowired
    private SoftwareService softwareService;

    @GetMapping("/getcompanies")
    public List<SoftwareCompany> getcompanies() {
        return softwareService.getCompanies();
    }
}
