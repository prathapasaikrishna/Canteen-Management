package com.canteen.management.dto;

import lombok.Data;

@Data
public class EmployeeResponse {

    private Long id;

    private Long organizationId;

    private Long branchId;

    private String employeeName;

    private String email;

    private String mobile;

    private String role;

    private String status;

    private String message;

}