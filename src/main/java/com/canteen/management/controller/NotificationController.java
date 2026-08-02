package com.canteen.management.controller;

import com.canteen.management.dto.NotificationResponse;
import com.canteen.management.service.NotificationService;
import com.canteen.management.dto.NotificationRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{studentId}")
    public List<NotificationResponse> getNotifications(
            @PathVariable String studentId) {

        return notificationService.getNotifications(studentId);
    }

    @PutMapping("/read/{studentId}")
    public ResponseEntity<Void> markRead(@PathVariable String studentId){

        notificationService.markAllRead(studentId);

        return ResponseEntity.ok().build();

    }

    @PostMapping("/test")
    public ResponseEntity<String> sendTestNotification(
            @RequestBody NotificationRequest request) {

        notificationService.sendPushNotification(
                request.getStudentId(),
                request.getTitle(),
                request.getMessage()
        );

        return ResponseEntity.ok("Notification Sent Successfully");
    }
}