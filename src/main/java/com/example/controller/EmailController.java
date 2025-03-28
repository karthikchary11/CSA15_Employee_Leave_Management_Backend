package com.example.controller;

import com.example.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        boolean emailSent = emailService.sendEmail(to, subject, body);
        return emailSent ? ResponseEntity.ok("Email sent successfully")
                         : ResponseEntity.status(500).body("Failed to send email");
    }
}
