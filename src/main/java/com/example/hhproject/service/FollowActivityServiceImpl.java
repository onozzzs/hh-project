package com.example.hhproject.service;

import com.example.hhproject.model.Activity;
import com.example.hhproject.model.Category;
import com.example.hhproject.model.Notification;
import com.example.hhproject.model.User;
import com.example.hhproject.repository.ActivityRepository;
import com.example.hhproject.repository.NotificationRepository;
import com.example.hhproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowActivityServiceImpl implements ActivityService {
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void makeAndSaveActivity(User user, Category category, String targetName) {
        String username = user.getUsername();
        User targetUser = userRepository.findByUsername(targetName);
        String content = category.makeContent(username, targetName);
        Activity activity = Activity.builder()
                .user(user)
                .content(content)
                .postId(0L)
                .category(category)
                .build();

        activityRepository.save(activity);

        Notification notification = Notification.builder()
                .receiver(targetUser.getId())
                .content(content)
                .activityId(activity.getId())
                .status(true)
                .isRead(false)
                .createdAt(activity.getCreatedAt())
                .build();
        notificationRepository.save(notification);
    }
}
