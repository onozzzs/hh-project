package com.example.hhproject.repository;

import com.example.hhproject.model.Activity;
import com.example.hhproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUserId(String user);
}
