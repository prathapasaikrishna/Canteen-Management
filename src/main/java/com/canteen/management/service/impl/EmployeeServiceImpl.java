package com.canteen.management.service.impl;

import com.canteen.management.dto.EmployeeRequest;
import com.canteen.management.dto.EmployeeResponse;
import com.canteen.management.entity.Branch;
import com.canteen.management.entity.BranchAdmin;
import com.canteen.management.entity.Employee;
import com.canteen.management.repository.BranchAdminRepository;
import com.canteen.management.repository.BranchRepository;
import com.canteen.management.repository.EmployeeRepository;
import com.canteen.management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchAdminRepository branchAdminRepository;

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest request) {

        if (employeeRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new RuntimeException("Employee Code already exists");
        }

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        BranchAdmin branchAdmin = branchAdminRepository.findById(request.getBranchAdminId())
                .orElseThrow(() -> new RuntimeException("Branch Admin not found"));

        Employee employee = new Employee();

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setName(request.getName());
        employee.setDesignation(request.getDesignation());
        employee.setEmail(request.getEmail());
        employee.setMobile(request.getMobile());
        employee.setPassword(request.getPassword());
        employee.setBranch(branch);
        employee.setBranchAdmin(branchAdmin);

        Employee saved = employeeRepository.save(employee);

        return new EmployeeResponse(
                saved.getId(),
                saved.getEmployeeCode(),
                saved.getName(),
                saved.getDesignation(),
                saved.getEmail(),
                saved.getMobile(),
                saved.getBranch().getId(),
                saved.getBranch().getBranchName(),
                saved.getBranchAdmin().getId(),
                saved.getBranchAdmin().getAdminName(),
                saved.getStatus()
        );
    }

    @Override
    public List<EmployeeResponse> getAllEmployees() {
        return null;
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        return null;
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        return null;
    }

    @Override
    public String deleteEmployee(Long id) {
        return null;
    }

    @Override
    public List<EmployeeResponse> getEmployeesByBranch(Long branchId) {
        return null;
    }
}