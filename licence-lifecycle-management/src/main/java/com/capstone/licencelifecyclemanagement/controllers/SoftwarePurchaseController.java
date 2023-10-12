package com.capstone.licencelifecyclemanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.services.SoftwarePurchaseService;

@RestController
@RequestMapping("/orders")
public class SoftwarePurchaseController {
    @Autowired
    private SoftwarePurchaseService softwarePurchaseService;

    @GetMapping("/softwares")
    public List<SoftwarePurchase> getAllSoftwaresOrders()
    {
        return softwarePurchaseService.getAll();
    }

}
