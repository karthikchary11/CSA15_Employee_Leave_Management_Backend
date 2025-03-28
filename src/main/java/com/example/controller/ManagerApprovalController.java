package com.example.controller;

import com.example.service.ManagerApprovalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/approval")
public class ManagerApprovalController {

    private final ManagerApprovalService managerApprovalService;

    public ManagerApprovalController(ManagerApprovalService managerApprovalService) {
        this.managerApprovalService = managerApprovalService;
    }

    // ✅ Approve a manager (Admin only)
    @PutMapping("/{managerId}/approve")
    public ResponseEntity<?> approveManager(@PathVariable String managerId) {
        return managerApprovalService.approveManager(managerId);
    }
}
