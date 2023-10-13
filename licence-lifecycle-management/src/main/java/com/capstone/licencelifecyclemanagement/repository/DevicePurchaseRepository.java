package com.capstone.licencelifecyclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;

@Repository
public interface DevicePurchaseRepository extends JpaRepository<DevicePurchase,Integer> {
    
}
