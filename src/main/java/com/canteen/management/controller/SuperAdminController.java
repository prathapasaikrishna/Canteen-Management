package com.canteen.management.controller;

import com.canteen.management.dto.SuperAdminDashboardResponse;
import com.canteen.management.dto.SuperAdminLoginRequest;
import com.canteen.management.dto.SuperAdminLoginResponse;
import com.canteen.management.service.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/super-admin")
@CrossOrigin("*")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @PostMapping("/login")
    public ResponseEntity<SuperAdminLoginResponse> login(
            @RequestBody SuperAdminLoginRequest request) {

        System.out.println(request.getEmail());
        System.out.println(request.getPassword());

        return ResponseEntity.ok(superAdminService.login(request));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<SuperAdminDashboardResponse> dashboard() {

        return ResponseEntity.ok(
                superAdminService.getDashboard()
        );
    }
}