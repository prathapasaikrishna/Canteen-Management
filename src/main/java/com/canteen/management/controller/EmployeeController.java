package com.canteen.management.controller;

import com.canteen.management.entity.Employee;
import com.canteen.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin("*")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/branch/{branchId}")
    public List<Employee> getEmployeesByBranch(@PathVariable Long branchId) {
        return employeeRepository.findByBranchId(branchId);
    }

    @PostMapping("/add")
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/status")
    public ResponseEntity<Employee> updateStatus(@RequestParam Long id, @RequestParam String status) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setStatus(status);
                    Employee updated = employeeRepository.save(employee);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
