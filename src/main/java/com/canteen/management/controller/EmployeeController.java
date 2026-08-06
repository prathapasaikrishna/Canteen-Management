package com.canteen.management.controller;

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

}