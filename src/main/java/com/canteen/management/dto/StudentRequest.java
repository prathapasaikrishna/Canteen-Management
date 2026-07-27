package com.canteen.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentRequest {

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Enter valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;

    @NotBlank(message = "Department is required")
    private String department;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must contain exactly 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Year is required")
    private String year;

    @NotBlank(message = "Role is required")
    private String role;

    private String canteenId;
}