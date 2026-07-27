package com.canteen.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.canteen.management.dto.StudentDashboardResponse;
import com.canteen.management.service.StudentDashboardService;

@RestController
@RequestMapping("/student-dashboard")
public class StudentDashboardController {

    @Autowired
    private StudentDashboardService studentDashboardService;

    @GetMapping("/{studentId}")
    public StudentDashboardResponse getDashboard(
            @PathVariable String studentId) {

        return studentDashboardService.getDashboard(studentId);
    }
}