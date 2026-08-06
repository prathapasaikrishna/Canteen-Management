package com.canteen.management.dto;

import lombok.Data;

@Data
public class UserLoginResponse {


    private String message;

    private String token;


    private Long id;

    private String name;

    private String email;

    private String role;


    private Long organizationId;

    private Long branchId;


    private String userType;

}