package com.example.hhproject.service;

import com.example.hhproject.dto.NotificationDTO;
import com.example.hhproject.model.Notification;
import com.example.hhproject.model.User;
import com.example.hhproject.repository.NotificationRepository;
import com.example.hhproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepository userRepository;

    public void addNotification(NotificationDTO notificationDTO) {
        User user = userRepository.findById(notificationDTO.getUserId()).orElseThrow(() -> new NoSuchElementException("user not found"));
        Notification notification = Notification.builder()
                .receiver(user)
                .content(notificationDTO.getContent())
                .notificationType(notificationDTO.getNotificationType())
                .build();

        notificationRepository.save(notification);
    }
}
