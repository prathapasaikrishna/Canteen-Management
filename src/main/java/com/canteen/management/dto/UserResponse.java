package com.canteen.management.dto;

import lombok.Data;

@Data
public class UserResponse {


    private Long id;

    private String name;

    private String email;

    private String mobileNumber;

    private String role;

    private Long organizationId;

    private Long branchId;

    private String userType;

    private String accountStatus;

    private String message;

}