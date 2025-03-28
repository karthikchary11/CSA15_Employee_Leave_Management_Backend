package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.LeaveRequest;
import com.example.service.LeaveService;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequest>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    @PostMapping
    public ResponseEntity<?> applyForLeave(@RequestBody LeaveRequest leaveRequest) {
        Optional<LeaveRequest> savedLeave = leaveService.applyForLeave(leaveRequest);
        if (savedLeave.isPresent()) {
            return ResponseEntity.ok(savedLeave.get());
        } 
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid leave request");
        }
    }

    @PutMapping("/{leaveId}/approve")
    public ResponseEntity<String> approveLeave(@PathVariable String leaveId) {
        if (leaveService.approveLeave(leaveId)) {
            return ResponseEntity.ok("Leave Approved!");
        }
        return ResponseEntity.status(404).body("Leave request not found or already processed");
    }

    @PutMapping("/{leaveId}/reject")
    public ResponseEntity<String> rejectLeave(@PathVariable String leaveId) {
        if (leaveService.rejectLeave(leaveId)) {
            return ResponseEntity.ok("Leave Rejected!");
        }
        return ResponseEntity.status(404).body("Leave request not found or already processed");
    }
}
