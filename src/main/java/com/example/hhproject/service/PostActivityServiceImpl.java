package com.example.hhproject.service;

import com.example.hhproject.dto.NotificationDTO;
import com.example.hhproject.model.*;
import com.example.hhproject.repository.ActivityRepository;
import com.example.hhproject.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class PostActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    PostRepository postRepository;

    @Override
    public void makeAndSaveActivity(User user, Category category, String targetName) {
        String username = user.getUsername();
        Long postId = Long.parseLong(targetName);
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));
        targetName = post.getTitle();

        String content = category.makeContent(username, targetName);
        Activity activity = Activity.builder()
                .user(user)
                .content(content)
                .postId(postId)
                .category(category)
                .build();

        activityRepository.save(activity);
        if (post.getWriter().getId() != user.getId()) {
            NotificationDTO notificationDTO = new NotificationDTO(activity);
            notificationService.updateNotification(post.getWriter().getId(), notificationDTO);
        }
    }
}
