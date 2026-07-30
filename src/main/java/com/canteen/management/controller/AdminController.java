package com.canteen.management.controller;

import com.canteen.management.dto.SalesFilterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.canteen.management.dto.DashboardResponse;
import com.canteen.management.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    private AdminService dashboardService;

    @GetMapping("/dashboard")
    public DashboardResponse getDashboard(@org.springframework.web.bind.annotation.RequestParam(value = "canteenId", required = false) String canteenId) {
        return adminService.getDashboard(canteenId);
    }

    @PostMapping("/dashboard/filter")
    public DashboardResponse getFilteredDashboard(
            @RequestBody SalesFilterRequest request){

        return dashboardService.getFilteredDashboard(
                request.getCanteenId(),
                request.getFilter()
        );
    }
}
