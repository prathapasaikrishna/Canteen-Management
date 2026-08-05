package com.canteen.management.dto;

import lombok.Data;

@Data
public class BranchAdminRequest {

    private Long organizationId;

    private Long branchId;

    private String adminName;

    private String email;

    private String password;

    private String mobile;

    private String role;

    private String status;

}