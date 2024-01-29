package com.example.hhproject.service;

import com.example.hhproject.dto.NotificationDTO;
import com.example.hhproject.model.Activity;
import com.example.hhproject.model.Notification;
import com.example.hhproject.model.User;
import com.example.hhproject.repository.ActivityRepository;
import com.example.hhproject.repository.NotificationRepository;
import com.example.hhproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private FollowService followService;

    public void updateNotification(final String userId, NotificationDTO notificationDTO) {
        Notification notification = NotificationDTO.toEntity(notificationDTO);
        notification.setReceiver(userId);

        notificationRepository.save(notification);
    }

    public List<Notification> getNotification(final String userId) {
        updateFollowingsNotification(userId);
        return notificationRepository.findByReceiver(userId);
    }

    public List<Notification> getNotificationToUser(final String userId) {
        return notificationRepository.findByReceiverAndStatus(userId, true);
    }

    private void updateFollowingsNotification(final String userId) {
        List<String> followings = followService.getFollowings(userId);
        for (String followingUserName : followings) {
            User followingUser = userRepository.findByUsername(followingUserName);
            List<Activity> followingActivities = activityRepository.findByUserId(followingUser.getId());
            for (Activity activity : followingActivities) {
                Optional<Notification> notification = notificationRepository.findByReceiverAndActivityId(userId, activity.getId());
                if (notification.isPresent()) continue;

                Notification newNotification = Notification.builder()
                        .receiver(userId)
                        .activityId(activity.getId())
                        .content(activity.getContent())
                        .createdAt(activity.getCreatedAt())
                        .status(false)
                        .build();
                notificationRepository.save(newNotification);
            }
        }
    }
}
