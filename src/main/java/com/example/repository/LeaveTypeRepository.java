package com.example.repository;

import com.example.model.LeaveType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LeaveTypeRepository extends MongoRepository<LeaveType, String> {
}
