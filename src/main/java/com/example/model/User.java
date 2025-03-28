package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;
    
    private String name;
    private String email;
    private String password;
    
    private Role role;  // Use Enum instead of String
    private String managerId; // Null for admins, assigned for employees
    private boolean approved = false; // Default false for managers
}
