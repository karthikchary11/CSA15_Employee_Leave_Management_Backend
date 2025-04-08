package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.LeaveRequest;
import com.example.service.LeaveService;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    // ✅ Fetch all leave requests (Admin/Manager)
    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    // ✅ Fetch leave history for a specific employee
    @GetMapping("/history/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getLeaveHistory(@PathVariable String employeeId) {
        return ResponseEntity.ok(leaveService.getLeaveHistory(employeeId));
    }

    // ✅ Apply for leave (Updated to validate required fields)
    @PostMapping
    public ResponseEntity<?> applyForLeave(@RequestBody LeaveRequest leaveRequest) {
        try {
            // Validate required fields
            if (leaveRequest.getUserId() == null || leaveRequest.getFromDate() == null || leaveRequest.getToDate() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required fields!");
            }

            Optional<LeaveRequest> savedLeave = leaveService.applyForLeave(leaveRequest);

            // ✅ Ensure consistent return type (ResponseEntity)
            if (savedLeave.isPresent()) {
                return ResponseEntity.ok(savedLeave.get()); // Return full leave object
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid leave request");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }

    // ✅ Approve or Reject leave request dynamically
    @PutMapping("/{leaveId}/status")
    public ResponseEntity<String> updateLeaveStatus(@PathVariable String leaveId, @RequestBody Map<String, String> body) {
        String status = body.get("status");
        if (status == null) {
            return ResponseEntity.status(400).body("Status is required!"); // ✅ Updated: Handle missing status field
        }
        if (leaveService.updateLeaveStatus(leaveId, status)) {
            return ResponseEntity.ok("Leave status updated to: " + status);
        }
        return ResponseEntity.status(400).body("Invalid leave ID or status");
    }
}
