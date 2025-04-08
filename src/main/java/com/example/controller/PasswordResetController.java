package com.example.controller;

import com.example.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password-reset") // ✅ Changed base path to avoid conflict
public class PasswordResetController {

    private final PasswordResetService resetService;

    public PasswordResetController(PasswordResetService resetService) {
        this.resetService = resetService;
    }

    @PostMapping("/request")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        boolean tokenGenerated = resetService.generateResetToken(email);
        return tokenGenerated ? ResponseEntity.ok("Password reset link sent to email")
                              : ResponseEntity.status(404).body("Email not found");
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean passwordReset = resetService.resetPassword(token, newPassword);
        return passwordReset ? ResponseEntity.ok("Password successfully reset")
                             : ResponseEntity.status(400).body("Invalid or expired token");
    }
}
