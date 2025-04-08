package com.example.controller;

import com.example.payload.LoginRequest;
import com.example.payload.RegisterRequest;
import com.example.payload.ForgotPasswordRequest;
import com.example.payload.ResetPasswordRequest;
import com.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // ✅ Allows frontend requests (Adjust if needed)
public class AuthController {

    private final AuthService authService;

    // ✅ Constructor-based injection (Best Practice)
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ✅ Login (Preserved)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    // ✅ Register (Preserved)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    // ✅ Forgot Password (Preserved)
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return authService.forgotPassword(request.getEmail());
    }

    // ✅ Reset Password (Preserved)
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return authService.resetPassword(resetPasswordRequest);
    }
}