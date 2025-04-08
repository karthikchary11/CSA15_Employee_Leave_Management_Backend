package com.example.controller;

import com.example.model.User;
import com.example.payload.UpdateUserRequest;
import com.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Get all users (Admin only)
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // ✅ Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<User> userOptional = userService.getUserById(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Update user (Admin or User updating own profile)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    // ✅ Delete user (Admin only)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

    // ✅ Approve Manager (Set `approved: true`)
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveManager(@PathVariable String id) {
        return userService.approveManager(id);
    }

    // ✅ Promote Employee to Manager or Admin
    @PutMapping("/{id}/role")
    public ResponseEntity<?> promoteUser(@PathVariable String id, @RequestBody UpdateUserRequest request) {
        boolean success = userService.updateUserRole(id, request.getRole());
        return success ? ResponseEntity.ok("User promoted successfully") : ResponseEntity.badRequest().body("Invalid role or operation not allowed");
    }
}
