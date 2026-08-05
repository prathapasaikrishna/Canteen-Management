package com.canteen.management.repository;

import com.canteen.management.entity.BranchAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchAdminRepository extends JpaRepository<BranchAdmin, Long> {

    Optional<BranchAdmin> findByEmail(String email);

    List<BranchAdmin> findByOrganizationId(Long organizationId);

    List<BranchAdmin> findByBranchId(Long branchId);

}