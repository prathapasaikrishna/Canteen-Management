package com.canteen.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.canteen.management.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    Student findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Student> findByStudentId(String studentId);

    Long countByBranchId(Long branchId);

    Long countByRole(String role);

    Long countByOrganizationId(Long organizationId);

    Long countByOrganizationIdAndRole(Long organizationId, String role);

    Long countByBranchIdAndRole(Long branchId, String role);

    Long countByCreatedAtAfterAndRole(java.time.LocalDateTime dateTime, String role);
}