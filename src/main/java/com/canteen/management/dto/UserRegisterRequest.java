package com.canteen.management.dto;

import lombok.Data;

@Data
public class UserRegisterRequest {


    private String name;

    private String email;

    private String password;

    private String mobileNumber;


    // CUSTOMER / ADMIN / SUPER_ADMIN
    private String role;


    private Long organizationId;

    private Long branchId;


    // CUSTOMER / ADMIN / SUPER_ADMIN
    private String userType;

}