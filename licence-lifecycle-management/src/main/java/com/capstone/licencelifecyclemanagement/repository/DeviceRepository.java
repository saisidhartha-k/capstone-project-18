package com.capstone.licencelifecyclemanagement.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.Device;
import com.capstone.licencelifecyclemanagement.entitys.DevicePurchase;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {

        @Query("SELECT s FROM Device s WHERE s.expiryDate <= :today")
        List<Device> findExpiredDevice(@Param("today") LocalDate today);

        @Query("SELECT s FROM Device s WHERE s.expiryDate > :today")
        List<Device> findNonExpiredDevice(@Param("today") LocalDate today);


}
