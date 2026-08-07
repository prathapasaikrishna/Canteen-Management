package com.canteen.management.repository;

import com.canteen.management.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByOrganizationId(Long organizationId);

    List<AuditLog> findByBranchId(Long branchId);

    List<AuditLog> findByUserId(String userId);

    List<AuditLog> findAllByOrderByCreatedAtDesc();
}