package com.example.service;

import com.example.model.User;
import com.example.model.Role;
import com.example.payload.LoginRequest;
import com.example.payload.RegisterRequest;
import com.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(409).body("Email already exists");
        }

        // ✅ Role is already an ENUM in the payload, no conversion needed
        Role role = registerRequest.getRole();

        // ✅ Create new user with Role Enum
        User newUser = new User();
        newUser.setName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(role); // ✅ No String conversion

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }
}
