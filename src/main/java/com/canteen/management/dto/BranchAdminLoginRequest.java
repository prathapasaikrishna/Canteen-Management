package com.canteen.management.dto;

import lombok.Data;

@Data
public class BranchAdminLoginRequest {

    private String email;

    private String password;

}