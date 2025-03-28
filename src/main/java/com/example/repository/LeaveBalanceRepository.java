package com.example.repository;

import com.example.model.LeaveBalance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface LeaveBalanceRepository extends MongoRepository<LeaveBalance, String> {
    Optional<LeaveBalance> findByUserIdAndLeaveTypeId(String userId, String leaveTypeId);
}
