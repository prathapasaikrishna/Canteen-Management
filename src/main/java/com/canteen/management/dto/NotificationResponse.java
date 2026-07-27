package com.canteen.management.dto;

import lombok.Data;

@Data
public class NotificationResponse {

    private Integer id;
    private String title;
    private String message;
    private String time;
    private boolean isRead;

}