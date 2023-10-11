package com.capstone.licencelifecyclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.Software;
import java.util.List;


@Repository
public interface SoftwareRepository extends JpaRepository <Software,Integer> {

    List<Software> findByIsExpired(Boolean isExpired);
    
}
