package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;

    private String employeeCode;
    private String name;
    private String designation;
    private String email;
    private String mobile;

    private Long branchId;
    private String branchName;

    private Long branchAdminId;
    private String branchAdminName;

    private String status;
}