package com.canteen.management.service.impl;

import com.canteen.management.dto.*;
import com.canteen.management.entity.Employee;
import com.canteen.management.repository.EmployeeRepository;
import com.canteen.management.security.JwtUtil;
import com.canteen.management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Override
    public EmployeeResponse addEmployee(EmployeeRequest request) {

        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Employee Already Exists");
        }

        Employee employee = new Employee();

        employee.setOrganizationId(request.getOrganizationId());
        employee.setBranchId(request.getBranchId());
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmail(request.getEmail());
        employee.setMobile(request.getMobile());

        employee.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        employee.setRole(request.getRole());
        employee.setStatus(request.getStatus());

        employee = employeeRepository.save(employee);

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setOrganizationId(employee.getOrganizationId());
        response.setBranchId(employee.getBranchId());
        response.setEmployeeName(employee.getEmployeeName());
        response.setEmail(employee.getEmail());
        response.setMobile(employee.getMobile());
        response.setRole(employee.getRole());
        response.setStatus(employee.getStatus());
        response.setMessage("Employee Added Successfully");

        return response;
    }

    @Override
    public EmployeeLoginResponse login(EmployeeLoginRequest request) {

        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));

        if (!passwordEncoder.matches(request.getPassword(), employee.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtUtil.generateToken(employee.getEmail());

        EmployeeLoginResponse response = new EmployeeLoginResponse();

        response.setId(employee.getId());
        response.setOrganizationId(employee.getOrganizationId());
        response.setBranchId(employee.getBranchId());
        response.setEmployeeName(employee.getEmployeeName());
        response.setEmail(employee.getEmail());
        response.setRole(employee.getRole());
        response.setStatus(employee.getStatus());
        response.setToken(token);
        response.setMessage("Login Successful");

        return response;
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(employee -> {

                    EmployeeResponse response = new EmployeeResponse();

                    response.setId(employee.getId());
                    response.setOrganizationId(employee.getOrganizationId());
                    response.setBranchId(employee.getBranchId());
                    response.setEmployeeName(employee.getEmployeeName());
                    response.setEmail(employee.getEmail());
                    response.setMobile(employee.getMobile());
                    response.setRole(employee.getRole());
                    response.setStatus(employee.getStatus());

                    return response;

                }).collect(Collectors.toList());

    }

    @Override
    public List<EmployeeResponse> getEmployeesByBranch(Long branchId) {

        return employeeRepository.findByBranchId(branchId)
                .stream()
                .map(employee -> {

                    EmployeeResponse response = new EmployeeResponse();

                    response.setId(employee.getId());
                    response.setOrganizationId(employee.getOrganizationId());
                    response.setBranchId(employee.getBranchId());
                    response.setEmployeeName(employee.getEmployeeName());
                    response.setEmail(employee.getEmail());
                    response.setMobile(employee.getMobile());
                    response.setRole(employee.getRole());
                    response.setStatus(employee.getStatus());

                    return response;

                }).collect(Collectors.toList());

    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));

        employee.setOrganizationId(request.getOrganizationId());
        employee.setBranchId(request.getBranchId());
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmail(request.getEmail());
        employee.setMobile(request.getMobile());
        employee.setRole(request.getRole());
        employee.setStatus(request.getStatus());

        if (request.getPassword() != null &&
                !request.getPassword().isEmpty()) {

            employee.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

        }

        employee = employeeRepository.save(employee);

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setOrganizationId(employee.getOrganizationId());
        response.setBranchId(employee.getBranchId());
        response.setEmployeeName(employee.getEmployeeName());
        response.setEmail(employee.getEmail());
        response.setMobile(employee.getMobile());
        response.setRole(employee.getRole());
        response.setStatus(employee.getStatus());
        response.setMessage("Employee Updated Successfully");

        return response;

    }

    @Override
    public String deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee Not Found"));

        employeeRepository.delete(employee);

        return "Employee Deleted Successfully";

    }
}