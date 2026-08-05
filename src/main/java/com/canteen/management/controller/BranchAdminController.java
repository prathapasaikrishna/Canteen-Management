package com.canteen.management.controller;

import com.canteen.management.dto.*;
import com.canteen.management.service.AdminService;
import com.canteen.management.service.BranchAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch-admin")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BranchAdminController {

    private final BranchAdminService service;


    @Autowired
    private AdminService adminService;


    @PostMapping("/add")
    public BranchAdminResponse add(
            @RequestBody BranchAdminRequest request) {

        return service.addBranchAdmin(request);
    }

    @PostMapping("/login")
    public BranchAdminLoginResponse login(
            @RequestBody BranchAdminLoginRequest request) {

        return service.login(request);
    }

    @GetMapping("/all")
    public List<BranchAdminResponse> all() {

        return service.getAllBranchAdmins();
    }

    @GetMapping("/branch/{branchId}")
    public List<BranchAdminResponse> byBranch(
            @PathVariable Long branchId) {

        return service.getBranchAdminsByBranch(branchId);
    }

    @PutMapping("/update/{id}")
    public BranchAdminResponse update(
            @PathVariable Long id,
            @RequestBody BranchAdminRequest request) {

        return service.updateBranchAdmin(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id) {

        return service.deleteBranchAdmin(id);
    }

    @GetMapping("/dashboard/{branchId}")
    public AdminDashboardResponse dashboard(
            @PathVariable Long branchId){

        return adminService.getBranchDashboard(branchId);
    }

    @GetMapping("/orders/today/{branchId}")
    public List<OrderResponse> todayOrders(
            @PathVariable Long branchId){

        return service.getTodayOrders(branchId);

    }

    @GetMapping("/orders/pending/{branchId}")
    public List<OrderResponse> pendingOrders(
            @PathVariable Long branchId){

        return service.getPendingOrders(branchId);

    }

    @GetMapping("/orders/preparing/{branchId}")
    public List<OrderResponse> preparingOrders(
            @PathVariable Long branchId){

        return service.getPreparingOrders(branchId);

    }

    @GetMapping("/orders/ready/{branchId}")
    public List<OrderResponse> readyOrders(
            @PathVariable Long branchId){

        return service.getReadyOrders(branchId);

    }

    @GetMapping("/orders/collected/{branchId}")
    public List<OrderResponse> collectedOrders(
            @PathVariable Long branchId){

        return service.getCollectedOrders(branchId);

    }

    @GetMapping("/orders/cancelled/{branchId}")
    public List<OrderResponse> cancelledOrders(
            @PathVariable Long branchId){

        return service.getCancelledOrders(branchId);

    }

    @PutMapping("/order/status")
    public OrderResponse updateStatus(
            @RequestBody UpdateOrderStatusRequest request){

        return service.updateOrderStatus(request);

    }
}