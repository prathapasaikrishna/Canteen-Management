package com.canteen.management.service;

import com.canteen.management.dto.EmployeeRequest;
import com.canteen.management.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse addEmployee(EmployeeRequest request);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse getEmployeeById(Long id);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    String deleteEmployee(Long id);

    List<EmployeeResponse> getEmployeesByBranch(Long branchId);
}