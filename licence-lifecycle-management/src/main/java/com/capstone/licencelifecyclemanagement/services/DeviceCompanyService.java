package com.capstone.licencelifecyclemanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.DeviceCompany;
import com.capstone.licencelifecyclemanagement.repository.DeviceCompanyRepository;

@Service
public class DeviceCompanyService {
    @Autowired
    private DeviceCompanyRepository DeviceCompanyRepository;

    public DeviceCompany addCompany(DeviceCompany company) {
        return DeviceCompanyRepository.save(company);
    }

    public List<DeviceCompany> allDeviceCompany ()
    {
        return DeviceCompanyRepository.findAll();
    }
}

