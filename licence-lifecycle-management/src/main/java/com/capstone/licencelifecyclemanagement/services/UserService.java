package com.capstone.licencelifecyclemanagement.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.dto.userDTO;
import com.capstone.licencelifecyclemanagement.entitys.User;
import com.capstone.licencelifecyclemanagement.repository.UserRepository;




@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByName(String username) {
        return userRepository.findByName("admin");
    }

 
}