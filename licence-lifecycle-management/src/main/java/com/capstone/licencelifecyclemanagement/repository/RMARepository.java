package com.capstone.licencelifecyclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.RMA;


@Repository
public interface RMARepository extends JpaRepository<RMA, Integer> {

}
