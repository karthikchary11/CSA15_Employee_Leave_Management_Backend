package com.example.service;

import com.example.model.ManagerApproval;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.ManagerApprovalRepository;
import com.example.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerApprovalService {

    private final ManagerApprovalRepository approvalRepository;
    private final UserRepository userRepository;

    public ManagerApprovalService(ManagerApprovalRepository approvalRepository, UserRepository userRepository) {
        this.approvalRepository = approvalRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all pending approvals
     */
    public List<ManagerApproval> getPendingApprovals() {
        return approvalRepository.findAll().stream().filter(m -> !m.isApproved()).toList();
    }

    /**
     * Approve a manager request (Admin Functionality)
     */
    @Transactional
    public ResponseEntity<String> approveManager(String userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<ManagerApproval> approvalOpt = approvalRepository.findByUserId(userId);

        if (userOpt.isEmpty() || approvalOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User or approval request not found");
        }

        User user = userOpt.get();
        ManagerApproval approval = approvalOpt.get();

        user.setRole(Role.MANAGER);  // Assign the role
        approval.setApproved(true);
        approval.setApprovedAt(LocalDateTime.now());

        userRepository.save(user);
        approvalRepository.save(approval);

        return ResponseEntity.ok("Manager approved successfully");
    }

    /**
     * Reject a manager request (Admin Functionality)
     */
    @Transactional
    public ResponseEntity<String> rejectManager(String userId) {
        Optional<ManagerApproval> approvalOpt = approvalRepository.findByUserId(userId);
        if (approvalOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Approval request not found");
        }

        approvalRepository.delete(approvalOpt.get());
        userRepository.deleteById(userId);

        return ResponseEntity.ok("Manager request rejected and user deleted");
    }
}
