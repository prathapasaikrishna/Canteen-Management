package com.canteen.management.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String studentId;
    private String name;
    private String mobileNumber;

}