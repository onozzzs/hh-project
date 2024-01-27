package com.example.hhproject.service;

import com.example.hhproject.model.Category;
import com.example.hhproject.model.User;
import org.springframework.stereotype.Service;

@Service
public interface ActivityService {
    void makeAndSaveActivity(User user, Category category, String targetName);
}
