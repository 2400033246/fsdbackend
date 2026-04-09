package com.example.backend1.service;

import com.example.backend1.model.User;
import com.example.backend1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // Here you would typically hash the password before saving
        // using BCryptPasswordEncoder, but we'll store plain text for simplicity
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
