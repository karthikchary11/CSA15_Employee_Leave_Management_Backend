package com.example.payload;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
    private String role;
}
