package com.canteen.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentResponse {

    private Integer id;
    private String studentId;
    private String name;
    private String email;
    private String department;
    private String mobileNumber;
    private String year;
    private String role;
    private String message;
}