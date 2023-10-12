package com.capstone.licencelifecyclemanagement.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.repository.SoftwarePurchaseRepository;

@Service
public class SoftwarePurchaseService {
    @Autowired
    private SoftwarePurchaseRepository softwarePurchaseRepository;

    public List<SoftwarePurchase> getAll()
    {
        return softwarePurchaseRepository.findAll();
    }
}
