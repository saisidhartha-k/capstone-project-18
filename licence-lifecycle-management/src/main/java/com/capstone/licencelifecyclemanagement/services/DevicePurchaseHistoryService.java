package com.capstone.licencelifecyclemanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.repository.DevicePurchaseRepository;

@Service
public class DevicePurchaseHistoryService {
     @Autowired
    private DevicePurchaseRepository devicePurchaseRepository;

    public List<DevicePurchase> getAll()
    {
        return devicePurchaseRepository.findAll();
    }
}
