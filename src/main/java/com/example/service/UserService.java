package com.example.service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;
import com.example.model.User;
import com.example.model.Role;
import com.example.payload.UpdateUserRequest;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ✅ Get all users
    public List<User> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            System.out.println("Fetched users: " + users.size()); // ✅ Debugging
            return users;
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList(); // Return empty list instead of breaking
        }
    }
    

    // ✅ Get user by ID
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    // ✅ Update user profile
    public ResponseEntity<?> updateUser(String id, UpdateUserRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(request.getName());
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Promote user (Prevent downgrading Admins)
    public boolean updateUserRole(String userId, String newRole) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            try {
                Role roleEnum = Role.valueOf(newRole.toUpperCase());

                // Prevent downgrading Admins
                if (user.getRole() == Role.ADMIN) {
                    return false;
                }

                user.setRole(roleEnum);
                userRepository.save(user);
                return true;
            } catch (IllegalArgumentException e) {
                return false; // Invalid role provided
            }
        }
        return false;
    }

    // ✅ Approve a Manager (`approved: true`)
    public ResponseEntity<?> approveManager(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if ("MANAGER".equals(user.getRole().toString())) {
                user.setApproved(true);
                userRepository.save(user);
                return ResponseEntity.ok("Manager approved successfully");
            }
            return ResponseEntity.badRequest().body("User is not a manager");
        }
        return ResponseEntity.notFound().build();
    }

    // ✅ Delete user (Prevent deleting Admin)
    public ResponseEntity<?> deleteUser(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getRole() == Role.ADMIN) {
                return ResponseEntity.badRequest().body("Cannot delete an Admin.");
            }
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}
