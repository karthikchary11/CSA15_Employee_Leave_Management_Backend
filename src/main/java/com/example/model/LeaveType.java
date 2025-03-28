package com.example.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Data
@Document(collection = "leave_types")
public class LeaveType {
    @Id
    private String id;
    private String name;
    private String description;
    private int maxDays; // Maximum days allowed for this leave type
}