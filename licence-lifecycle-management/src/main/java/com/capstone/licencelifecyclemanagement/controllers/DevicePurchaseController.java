package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;
import com.capstone.licencelifecyclemanagement.services.DevicePurchaseHistoryService;
import com.capstone.licencelifecyclemanagement.services.DeviceService;

@CrossOrigin()
@RestController
@RequestMapping("/devicehistory")
public class DevicePurchaseController {

    @Autowired
    private DevicePurchaseHistoryService devicePurchaseHistoryService;
    
    @GetMapping("/devices")
    public List<DevicePurchase> getDevicePurchase()
    {
        return devicePurchaseHistoryService.getAll();
    }

}
