package com.canteen.management.service.impl;

import com.canteen.management.dto.EmployeeLoginRequest;
import com.canteen.management.dto.EmployeeLoginResponse;
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

        return employeeRepository.findAll()
                .stream()
                .map(employee -> new EmployeeResponse(

                        employee.getId(),
                        employee.getEmployeeCode(),
                        employee.getName(),
                        employee.getDesignation(),
                        employee.getEmail(),
                        employee.getMobile(),

                        employee.getBranch().getId(),
                        employee.getBranch().getBranchName(),

                        employee.getBranchAdmin().getId(),
                        employee.getBranchAdmin().getAdminName(),

                        employee.getStatus()

                ))
                .toList();
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return new EmployeeResponse(

                employee.getId(),
                employee.getEmployeeCode(),
                employee.getName(),
                employee.getDesignation(),
                employee.getEmail(),
                employee.getMobile(),

                employee.getBranch().getId(),
                employee.getBranch().getBranchName(),

                employee.getBranchAdmin().getId(),
                employee.getBranchAdmin().getAdminName(),

                employee.getStatus()
        );
    }

    @Override
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Branch branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        BranchAdmin branchAdmin = branchAdminRepository.findById(request.getBranchAdminId())
                .orElseThrow(() -> new RuntimeException("Branch Admin not found"));

        employee.setEmployeeCode(request.getEmployeeCode());
        employee.setName(request.getName());
        employee.setDesignation(request.getDesignation());
        employee.setEmail(request.getEmail());
        employee.setMobile(request.getMobile());
        employee.setPassword(request.getPassword());

        employee.setBranch(branch);
        employee.setBranchAdmin(branchAdmin);

        Employee updated = employeeRepository.save(employee);

        return new EmployeeResponse(
                updated.getId(),
                updated.getEmployeeCode(),
                updated.getName(),
                updated.getDesignation(),
                updated.getEmail(),
                updated.getMobile(),
                updated.getBranch().getId(),
                updated.getBranch().getBranchName(),
                updated.getBranchAdmin().getId(),
                updated.getBranchAdmin().getAdminName(),
                updated.getStatus()
        );
    }

    @Override
    public String deleteEmployee(Long id) {

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepository.delete(employee);

        return "Employee Deleted Successfully";
    }

    @Override
    public List<EmployeeResponse> getEmployeesByBranch(Long branchId) {

        return employeeRepository.findByBranchId(branchId)
                .stream()
                .map(employee -> new EmployeeResponse(

                        employee.getId(),
                        employee.getEmployeeCode(),
                        employee.getName(),
                        employee.getDesignation(),
                        employee.getEmail(),
                        employee.getMobile(),

                        employee.getBranch().getId(),
                        employee.getBranch().getBranchName(),

                        employee.getBranchAdmin().getId(),
                        employee.getBranchAdmin().getAdminName(),

                        employee.getStatus()

                ))
                .toList();
    }

    @Override
    public EmployeeLoginResponse login(EmployeeLoginRequest request) {

        Employee employee = employeeRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        EmployeeLoginResponse response = new EmployeeLoginResponse();

        response.setMessage("Login Successful");

        response.setEmployeeId(employee.getId());
        response.setEmployeeCode(employee.getEmployeeCode());
        response.setEmployeeName(employee.getName());
        response.setDesignation(employee.getDesignation());
        response.setEmail(employee.getEmail());

        response.setBranchId(employee.getBranch().getId());
        response.setBranchName(employee.getBranch().getBranchName());

        response.setStatus(employee.getStatus());

// Organization entity Branch లో ఉంటే
        response.setOrganizationId(
                employee.getBranch()
                        .getOrganization()
                        .getId()
        );

        response.setToken("");

        return response;
    }
}