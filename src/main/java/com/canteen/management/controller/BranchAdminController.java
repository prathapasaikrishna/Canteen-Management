package com.canteen.management.controller;

import com.canteen.management.dto.*;
import com.canteen.management.service.BranchAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch-admin")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BranchAdminController {

    private final BranchAdminService service;

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
}