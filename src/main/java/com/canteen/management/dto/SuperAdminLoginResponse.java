package com.canteen.management.dto;

import lombok.Data;

@Data
public class SuperAdminLoginResponse {

    private Long id;

    private String name;

    private String email;

    private String role;

    private String status;

    private String message;

}