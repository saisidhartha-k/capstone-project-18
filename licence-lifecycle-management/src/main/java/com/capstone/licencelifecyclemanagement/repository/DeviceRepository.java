package com.capstone.licencelifecyclemanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Integer> {
        List<Device> findByIsExpired(Boolean isExpired);

}
