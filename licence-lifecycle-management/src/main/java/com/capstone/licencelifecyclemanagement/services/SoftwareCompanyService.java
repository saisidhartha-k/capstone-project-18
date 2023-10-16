package com.capstone.licencelifecyclemanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;
import com.capstone.licencelifecyclemanagement.repository.SoftwareCompanyRepository;

@Service
public class SoftwareCompanyService {

    @Autowired
    private SoftwareCompanyRepository softwareCompanyRepository; 

    public List<SoftwareCompany> getSoftwareCompanies()
    {
        return softwareCompanyRepository.findAll();
    }
    
}
