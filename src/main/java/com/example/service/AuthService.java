package com.example.service;

import com.example.model.User;
import com.example.model.Role;
import com.example.payload.LoginRequest;
import com.example.payload.RegisterRequest;
import com.example.payload.ResetPasswordRequest;
import com.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService; // ✅ Added EmailService

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // ✅ Login Function (Preserved)
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent() && passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword())) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // ✅ Register Function (Preserved)
    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(409).body("Email already exists");
        }

        Role role = registerRequest.getRole();

        User newUser = new User();
        newUser.setName(registerRequest.getName());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setRole(role);

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered successfully");
    }

    // ✅ Forgot Password Function (NEW)
    public ResponseEntity<?> forgotPassword(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User user = userOptional.get();
        String resetToken = UUID.randomUUID().toString(); // ✅ Generate Reset Token
        user.setResetToken(resetToken); // ✅ Save Token in User Model
        userRepository.save(user);

        // ✅ Send Reset Email
        String resetLink = "https://nhtdv9-3000.bytexl.dev/reset-password?token=" + resetToken;
        String subject = "Password Reset Request";
        String body = "Click the link below to reset your password:\n" + resetLink;

        boolean emailSent = emailService.sendEmail(user.getEmail(), subject, body);
        if (!emailSent) {
            return ResponseEntity.status(500).body("Failed to send reset email");
        }

        return ResponseEntity.ok("Password reset email sent successfully");
    }

    // ✅ Reset Password Function (NEW)
    public ResponseEntity<?> resetPassword(ResetPasswordRequest resetPasswordRequest) {
        Optional<User> userOptional = userRepository.findByResetToken(resetPasswordRequest.getToken());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(400).body("Invalid or expired token");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword())); // ✅ Encrypt new password
        user.setResetToken(null); // ✅ Clear reset token
        userRepository.save(user);

        return ResponseEntity.ok("Password reset successfully");
    }
}
