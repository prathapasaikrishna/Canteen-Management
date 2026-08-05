package com.canteen.management.dto;

import lombok.Data;

@Data
public class EmployeeLoginResponse {

    private Long id;

    private Long organizationId;

    private Long branchId;

    private String employeeName;

    private String email;

    private String role;

    private String status;

    private String token;

    private String message;

}