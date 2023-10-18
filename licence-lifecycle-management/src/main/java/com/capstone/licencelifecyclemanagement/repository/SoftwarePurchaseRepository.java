package com.capstone.licencelifecyclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchase;
import com.capstone.licencelifecyclemanagement.entitys.SoftwarePurchaseId;

import jakarta.transaction.Transactional;

@Repository
public interface SoftwarePurchaseRepository extends JpaRepository<SoftwarePurchase , SoftwarePurchaseId> {
    @Transactional
    void deleteBySoftwarePurchaseId_Software_Id(int softwareId);}
