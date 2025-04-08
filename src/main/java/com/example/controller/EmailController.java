package com.example.controller;

import com.example.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody(required = false) Map<String, String> emailData,
                                            @RequestParam(required = false) String to,
                                            @RequestParam(required = false) String subject,
                                            @RequestParam(required = false) String body) {
        // Debug log to check incoming request
        System.out.println("Received email request: " + emailData + " | To: " + to + ", Subject: " + subject + ", Body: " + body);

        // Extract from JSON body if available
        if (emailData != null) {
            to = emailData.getOrDefault("to", to);
            subject = emailData.getOrDefault("subject", subject);
            body = emailData.getOrDefault("body", body);
        }

        // Validate required fields
        if (to == null || subject == null || body == null) {
            System.err.println("❌ Missing fields: 'to', 'subject', or 'body'");
            return ResponseEntity.badRequest().body("Missing required fields: 'to', 'subject', or 'body'");
        }

        boolean emailSent = emailService.sendEmail(to, subject, body);
        return emailSent ? ResponseEntity.ok("✅ Email sent successfully")
                         : ResponseEntity.status(500).body("❌ Failed to send email");
    }
}
