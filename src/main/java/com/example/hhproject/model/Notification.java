package com.example.hhproject.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="notification_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name ="receiver_id")
    private User receiver;

    @Nullable
    private String content;

    private NotificationType notificationType;
}
