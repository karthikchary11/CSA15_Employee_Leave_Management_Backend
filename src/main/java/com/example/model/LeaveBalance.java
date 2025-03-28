package com.example.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leave_balances")
@Data
public class LeaveBalance {
    @Id
    private String id;
    private String userId;       // Employee ID
    private String leaveTypeId;  // Type of leave
    private int remainingDays;   // Remaining days of leave
}
