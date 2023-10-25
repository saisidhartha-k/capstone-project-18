package com.capstone.licencelifecyclemanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.licencelifecyclemanagement.entitys.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
    
    Optional<User> findByName(String name);
    Optional<User> findById(Integer id);
    Boolean existsByName(String name);

}
