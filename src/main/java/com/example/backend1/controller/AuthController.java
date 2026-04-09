package com.example.backend1.controller;

import com.example.backend1.dto.LoginRequest;
import com.example.backend1.dto.SignupRequest;
import com.example.backend1.model.User;
import com.example.backend1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        userService.saveUser(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userService.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Validate password and Role
            if (user.getPassword().equals(request.getPassword()) && user.getRole() == request.getRole()) {
                return ResponseEntity.ok(user);
            } else if (!user.getPassword().equals(request.getPassword())) {
                return ResponseEntity.badRequest().body("Error: Invalid password!");
            } else {
                return ResponseEntity.status(403).body("Error: Role mismatch. Please log in through the correct portal.");
            }
        }

        return ResponseEntity.badRequest().body("Error: User not found!");
    }
}
