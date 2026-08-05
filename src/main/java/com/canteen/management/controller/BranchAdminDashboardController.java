package com.canteen.management.controller;

import com.canteen.management.dto.BranchDashboardResponse;
import com.canteen.management.service.BranchAdminDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/branch-admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class BranchAdminDashboardController {

    private final BranchAdminDashboardService service;

    @GetMapping("/dashboard/{branchId}")
    public BranchDashboardResponse dashboard(
            @PathVariable Long branchId) {

        return service.getDashboard(branchId);
    }
}