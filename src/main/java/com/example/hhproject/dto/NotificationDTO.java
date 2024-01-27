package com.example.hhproject.dto;

import com.example.hhproject.model.Activity;
import com.example.hhproject.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private String receiver;
    private Long activityId;
    private String content;
    private LocalDateTime createdAt;
    private Boolean status;

    public NotificationDTO(Activity activity) {
        this.activityId = activity.getId();
        this.content = activity.getContent();
        this.createdAt = activity.getCreatedAt();
    }

    public static Notification toEntity(NotificationDTO notificationDTO) {
        return Notification.builder()
                .activityId(notificationDTO.getActivityId())
                .content(notificationDTO.getContent())
                .createdAt(notificationDTO.getCreatedAt())
                .build();
    }
}
