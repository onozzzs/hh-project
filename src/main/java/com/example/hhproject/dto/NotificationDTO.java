package com.example.hhproject.dto;

import com.example.hhproject.model.NotificationType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationDTO {
    private String userId;
    private String content;
    private NotificationType notificationType;
}
