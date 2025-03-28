package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "leave_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    @Id
    private String id;

    private String userId;
    private String leaveTypeId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    private LeaveStatus status; // Using Enum instead of String
    private String managerId;
    private LocalDate createdAt = LocalDate.now(); // Default to current date
}
