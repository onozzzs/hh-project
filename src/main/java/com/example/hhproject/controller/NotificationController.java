package com.example.hhproject.controller;

import com.example.hhproject.dto.NotificationResponseDTO;
import com.example.hhproject.model.Notification;
import com.example.hhproject.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getNotification(@AuthenticationPrincipal String userId) {
        List<Notification> notifications = notificationService.getNotification(userId);
        List<NotificationResponseDTO> responseDTOs = notifications.stream().map(NotificationResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }

    @GetMapping("/toUser")
    public ResponseEntity<?> getNotificationToUser(@AuthenticationPrincipal String userId) {
        List<Notification> notifications = notificationService.getNotificationToUser(userId);
        List<NotificationResponseDTO> responseDTOs = notifications.stream().map(NotificationResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }
}
