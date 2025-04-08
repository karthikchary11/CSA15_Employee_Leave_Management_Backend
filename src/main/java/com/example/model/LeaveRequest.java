package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "leave_requests")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequest {
    @Id
    private String id;

    @Field("user_id") // Explicit MongoDB field name
    private String userId;

    @Field("leave_type_id")
    private String leaveTypeId;

    @Field("from_date")
    private LocalDate fromDate;

    @Field("to_date")
    private LocalDate toDate;

    @Field("reason")
    private String reason;

    @Field("status")
    private LeaveStatus status; // Enum stored as STRING

    @Field("manager_id")
    private String managerId;

    @CreatedDate
    @Field("created_at")
    private LocalDate createdAt; // Automatically set when saved
}
