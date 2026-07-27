package com.canteen.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canteen.management.dto.DashboardResponse;
import com.canteen.management.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard(@org.springframework.web.bind.annotation.RequestParam(value = "canteenId", required = false) String canteenId) {
        return adminService.getDashboard(canteenId);
    }
}
