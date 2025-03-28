package com.example.repository;

import com.example.model.LeaveRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface LeaveRequestRepository extends MongoRepository<LeaveRequest, String> {

    List<LeaveRequest> findByUserId(String userId);
    List<LeaveRequest> findByManagerIdAndStatus(String managerId, String status);
}
