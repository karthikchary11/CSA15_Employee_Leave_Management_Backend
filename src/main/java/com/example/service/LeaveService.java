package com.example.service;

import com.example.model.LeaveRequest;
import com.example.model.LeaveStatus;
import com.example.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    // ✅ Fetch all leave requests (Admin/Manager)
    public List<LeaveRequest> getAllLeaves() {
        return leaveRequestRepository.findAll();
    }

    // ✅ Fetch leave history for a specific employee
    public List<LeaveRequest> getLeaveHistory(String employeeId) {
        return leaveRequestRepository.findByUserId(employeeId);
    }

    // ✅ Apply for leave (Updated to validate required fields)
    public Optional<LeaveRequest> applyForLeave(LeaveRequest leaveRequest) {
        if (leaveRequest.getUserId() == null || leaveRequest.getFromDate() == null || leaveRequest.getToDate() == null) {
            return Optional.empty(); // ✅ Updated: Return empty optional if required fields are missing
        }
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequest.setCreatedAt(java.time.LocalDate.now());
        return Optional.of(leaveRequestRepository.save(leaveRequest));
    }

    // ✅ Approve or Reject leave dynamically (Updated to handle null status)
    public boolean updateLeaveStatus(String leaveId, String status) {
        Optional<LeaveRequest> leaveOptional = leaveRequestRepository.findById(leaveId);
        if (leaveOptional.isPresent()) {
            LeaveRequest leave = leaveOptional.get();
            if (leave.getStatus() == LeaveStatus.PENDING) {
                if ("APPROVED".equalsIgnoreCase(status)) {
                    leave.setStatus(LeaveStatus.APPROVED);
                } else if ("REJECTED".equalsIgnoreCase(status)) {
                    leave.setStatus(LeaveStatus.REJECTED);
                } else {
                    return false; // ✅ Updated: Handle invalid status
                }
                leaveRequestRepository.save(leave);
                return true;
            }
        }
        return false;
    }
}
