package com.canteen.management.dto;

import lombok.Data;

@Data
public class EmployeeRequest {

    private String employeeCode;
    private String name;
    private String designation;
    private String email;
    private String mobile;
    private String password;

    private Long branchId;
    private Long branchAdminId;
}