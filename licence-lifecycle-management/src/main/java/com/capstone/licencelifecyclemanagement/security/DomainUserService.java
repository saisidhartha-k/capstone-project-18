package com.capstone.licencelifecyclemanagement.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.licencelifecyclemanagement.entitys.User;
import com.capstone.licencelifecyclemanagement.repository.UserRepository;

@Service
public class DomainUserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public DomainUserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> optUser = userRepository.findByName(username);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!optUser.get().getPassword().equals(password)) {
            return Optional.empty();
        }
        return optUser;
    }

    public User create(User user) {
        user.setPassword("{bcrypt}" + passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }

    public Boolean existsByName(String name) {
        return userRepository.existsByName(name);
    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

}