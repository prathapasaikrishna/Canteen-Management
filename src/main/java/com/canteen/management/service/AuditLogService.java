package com.canteen.management.service;

public interface AuditLogService {

    void saveLog(
            String userType,
            String userId,
            String action,
            String description,
            Long organizationId,
            Long branchId
    );

}