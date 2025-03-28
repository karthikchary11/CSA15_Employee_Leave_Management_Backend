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

    public List<LeaveRequest> getAllLeaves() {
        return leaveRequestRepository.findAll();
    }

    public Optional<LeaveRequest> applyForLeave(LeaveRequest leaveRequest) {
        leaveRequest.setStatus(LeaveStatus.PENDING);  // ✅ Use Enum
        leaveRequest.setCreatedAt(java.time.LocalDate.now());
        return Optional.of(leaveRequestRepository.save(leaveRequest));
    }

    public boolean approveLeave(String leaveId) {
        Optional<LeaveRequest> leaveOptional = leaveRequestRepository.findById(leaveId);
        if (leaveOptional.isPresent() && leaveOptional.get().getStatus() == LeaveStatus.PENDING) {  // ✅ Enum Comparison
            LeaveRequest leave = leaveOptional.get();
            leave.setStatus(LeaveStatus.APPROVED);  // ✅ Use Enum
            leaveRequestRepository.save(leave);
            return true;
        }
        return false;
    }

    public boolean rejectLeave(String leaveId) {
        Optional<LeaveRequest> leaveOptional = leaveRequestRepository.findById(leaveId);
        if (leaveOptional.isPresent() && leaveOptional.get().getStatus() == LeaveStatus.PENDING) {  // ✅ Enum Comparison
            LeaveRequest leave = leaveOptional.get();
            leave.setStatus(LeaveStatus.REJECTED);  // ✅ Use Enum
            leaveRequestRepository.save(leave);
            return true;
        }
        return false;
    }
}
