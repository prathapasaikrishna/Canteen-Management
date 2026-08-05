package com.canteen.management.repository;

import com.canteen.management.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    Optional<Employee> findByEmail(String email);


    List<Employee> findByBranchId(Long branchId);


    Long countByBranchId(Long branchId);


    List<Employee> findByOrganizationId(Long organizationId);

}