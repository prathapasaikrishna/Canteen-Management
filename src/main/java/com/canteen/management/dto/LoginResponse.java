package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String message;
    private String token;

    private String studentId;
    private String role;
    private String canteenId;

    private String email;
    private String studentName;
    private String mobileNumber;

    private Long organizationId;
    private Long branchId;

    public LoginResponse(String message) {
        this.message = message;
    }

    public LoginResponse(String message, String token) {
        this.message = message;
        this.token = token;
    }
}