package com.example.hhproject.service;

import com.example.hhproject.model.*;
import com.example.hhproject.repository.CommentRepository;
import com.example.hhproject.repository.PostRepository;
import com.example.hhproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostActivityServiceImpl notificationService;

    public void addComment(final String userId, final Long postId, Comment comment) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));

        comment.setUser(user);
        comment.setPost(post);

        commentRepository.save(comment);

        notificationService.makeAndSaveActivity(user, Category.COMMENT, String.valueOf(post.getId()));
    }
}
