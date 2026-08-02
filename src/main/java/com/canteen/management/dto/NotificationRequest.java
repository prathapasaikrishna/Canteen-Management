package com.canteen.management.dto;

import lombok.Data;

@Data
public class NotificationRequest {

    private String studentId;
    private String title;
    private String message;

}