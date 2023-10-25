package com.capstone.licencelifecyclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;

import jakarta.transaction.Transactional;

@Repository
public interface DevicePurchaseRepository extends JpaRepository<DevicePurchase, Integer> {
    @Transactional
    void deleteByDevicePurchaseId_Device_Id(int deviceId);

    List<DevicePurchase> findByDevicePurchaseId_Device_Id(int deviceId);

}
