package com.canteen.management.dto;

import lombok.Data;

@Data
public class EmployeeLoginResponse {

    private String message;

    private Long employeeId;

    private String employeeCode;

    private String employeeName;

    private String designation;

    private String email;

    private Long organizationId;

    private Long branchId;

    private String branchName;

    private String status;

    private String token;

}