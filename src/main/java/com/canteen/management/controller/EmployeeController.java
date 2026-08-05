package com.canteen.management.controller;

import com.canteen.management.dto.EmployeeLoginRequest;
import com.canteen.management.dto.EmployeeLoginResponse;
import com.canteen.management.dto.EmployeeRequest;
import com.canteen.management.dto.EmployeeResponse;
import com.canteen.management.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/add")
    public EmployeeResponse addEmployee(
            @RequestBody EmployeeRequest request) {

        return employeeService.addEmployee(request);
    }

    @PostMapping("/login")
    public EmployeeLoginResponse login(
            @RequestBody EmployeeLoginRequest request) {

        return employeeService.login(request);
    }

    @GetMapping("/all")
    public List<EmployeeResponse> getAllEmployees() {

        return employeeService.getAllEmployees();
    }

    @GetMapping("/branch/{branchId}")
    public List<EmployeeResponse> getEmployeesByBranch(
            @PathVariable Long branchId) {

        return employeeService.getEmployeesByBranch(branchId);
    }

    @PutMapping("/update/{id}")
    public EmployeeResponse updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(
            @PathVariable Long id) {

        return employeeService.deleteEmployee(id);
    }
}