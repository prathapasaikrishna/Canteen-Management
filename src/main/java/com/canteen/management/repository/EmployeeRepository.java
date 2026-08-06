package com.canteen.management.repository;

import com.canteen.management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByEmail(String email);

    List<Employee> findByBranchId(Long branchId);
}