package com.canteen.management.dto;

import lombok.Data;

@Data
public class EmployeeRequest {

    private Long organizationId;

    private Long branchId;

    private String employeeName;

    private String email;

    private String mobile;

    private String password;

    private String role;

    private String status;

}