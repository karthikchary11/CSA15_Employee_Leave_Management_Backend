package com.example.repository;

import com.example.model.ManagerApproval;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerApprovalRepository extends MongoRepository<ManagerApproval, String> {
    Optional<ManagerApproval> findByUserId(String userId);

    // Update approval status
    default void approveManager(ManagerApproval managerApproval) {
        managerApproval.setApproved(true);
        save(managerApproval);
    }

    default void rejectManager(ManagerApproval managerApproval) {
        managerApproval.setApproved(false);
        save(managerApproval);
    }
}
