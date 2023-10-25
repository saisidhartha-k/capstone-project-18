package com.capstone.licencelifecyclemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.DecommissionedItem;

@Repository
public interface DecommisionedItemRepository extends JpaRepository <DecommissionedItem,Integer> {
    
}
