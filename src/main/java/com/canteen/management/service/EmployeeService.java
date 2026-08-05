package com.canteen.management.service;

import com.canteen.management.dto.EmployeeLoginRequest;
import com.canteen.management.dto.EmployeeLoginResponse;
import com.canteen.management.dto.EmployeeRequest;
import com.canteen.management.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse addEmployee(EmployeeRequest request);

    EmployeeLoginResponse login(EmployeeLoginRequest request);

    List<EmployeeResponse> getAllEmployees();

    List<EmployeeResponse> getEmployeesByBranch(Long branchId);

    EmployeeResponse updateEmployee(Long id, EmployeeRequest request);

    String deleteEmployee(Long id);

}