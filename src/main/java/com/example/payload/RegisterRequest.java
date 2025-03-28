package com.example.payload;

import com.example.model.Role;
import lombok.Data;
@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;  // Change from String to Role ENUM
}
