package com.canteen.management.controller;

import com.canteen.management.dto.NotificationResponse;
import com.canteen.management.service.NotificationService;

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
}