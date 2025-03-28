package com.example.payload;

import com.example.model.Role;

public record UserDTO(String id, String name, String email, Role role) {}
