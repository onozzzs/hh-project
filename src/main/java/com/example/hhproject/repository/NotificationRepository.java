package com.example.hhproject.repository;

import com.example.hhproject.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiver(String receiver);
    List<Notification> findByReceiverAndStatus(String receiver, Boolean status);
    Optional<Notification> findByReceiverAndActivityId(String receiver, Long activityId);
}
