package com.canteen.management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String studentId;

    private String title;

    private String message;

    private String time;

    private boolean isRead;
}