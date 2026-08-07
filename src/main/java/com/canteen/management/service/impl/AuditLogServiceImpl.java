    package com.canteen.management.service.impl;

    import com.canteen.management.entity.AuditLog;
    import com.canteen.management.repository.AuditLogRepository;
    import com.canteen.management.service.AuditLogService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    @Service
    public class AuditLogServiceImpl implements AuditLogService {

        @Autowired
        private AuditLogRepository repository;

        @Override
        public void saveLog(
                String userType,
                String userId,
                String action,
                String description,
                Long organizationId,
                Long branchId) {

            AuditLog log = new AuditLog();

            log.setUserType(userType);
            log.setUserId(userId);
            log.setAction(action);
            log.setDescription(description);
            log.setOrganizationId(organizationId);
            log.setBranchId(branchId);

            repository.save(log);
        }
    }