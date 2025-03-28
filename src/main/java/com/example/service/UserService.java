package com.example.service;

import com.example.model.User;
import com.example.model.Role;
import com.example.payload.UpdateUserRequest;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean updateUserRole(String userId, String newRole) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Convert String to Enum safely
            try {
                Role roleEnum = Role.valueOf(newRole.toUpperCase()); // Convert input string to ENUM
                if (user.getRole() != Role.ADMIN) { // Prevent downgrading Admins
                    user.setRole(roleEnum);
                    userRepository.save(user);
                    return true;
                }
            } catch (IllegalArgumentException e) {
                return false; // Invalid role provided
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

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

    public ResponseEntity<?> deleteUser(String id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
