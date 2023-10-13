package com.capstone.licencelifecyclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.SoftwareCompany;

@Repository
public interface SoftwareCompanyRepository extends JpaRepository<SoftwareCompany,Integer> {
    
}
