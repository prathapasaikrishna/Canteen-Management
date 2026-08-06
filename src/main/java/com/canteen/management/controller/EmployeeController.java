package com.canteen.management.controller;

import com.canteen.management.dto.EmployeeLoginRequest;
import com.canteen.management.dto.EmployeeLoginResponse;
import com.canteen.management.dto.EmployeeRequest;
import com.canteen.management.dto.EmployeeResponse;
import com.canteen.management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public EmployeeResponse addEmployee(@RequestBody EmployeeRequest request) {
        return employeeService.addEmployee(request);
    }

    @GetMapping("/all")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/update/{id}")
    public EmployeeResponse updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return employeeService.updateEmployee(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    @GetMapping("/branch/{branchId}")
    public List<EmployeeResponse> getEmployeesByBranch(
            @PathVariable Long branchId) {

        return employeeService.getEmployeesByBranch(branchId);
    }

    @PostMapping("/login")
    public EmployeeLoginResponse login(
            @RequestBody EmployeeLoginRequest request) {

        return employeeService.login(request);
    }

}