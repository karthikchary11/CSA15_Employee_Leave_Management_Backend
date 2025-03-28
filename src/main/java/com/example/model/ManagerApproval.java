package com.example.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "manager_approvals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerApproval {
    @Id
    private String id;

    private String userId;
    private boolean approved = false;
    private LocalDateTime requestedAt = LocalDateTime.now(); // Set when request is made
    private LocalDateTime approvedAt; // Null until approved
}
