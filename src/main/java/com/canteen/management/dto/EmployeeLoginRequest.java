package com.canteen.management.dto;

import lombok.Data;

@Data
public class EmployeeLoginRequest {

    private String email;

    private String password;

}