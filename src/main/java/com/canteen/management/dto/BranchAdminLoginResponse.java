package com.canteen.management.dto;

import lombok.Data;

@Data
public class BranchAdminLoginResponse {

    private String token;

    private Long id;

    private Long organizationId;

    private Long branchId;

    private String adminName;

    private String email;

    private String role;

    private String status;

    private String message;
}