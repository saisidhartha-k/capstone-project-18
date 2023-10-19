package com.capstone.licencelifecyclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.Software;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Integer> {

    @Query("SELECT s FROM Software s WHERE s.expiryDate <= :today")
    List<Software> findExpiredSoftware(@Param("today") LocalDate today);

    @Query("SELECT s FROM Software s WHERE s.expiryDate > :today")
    List<Software> findNonExpiredSoftware(@Param("today") LocalDate today);
}
