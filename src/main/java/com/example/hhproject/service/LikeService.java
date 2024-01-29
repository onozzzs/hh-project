package com.example.hhproject.service;

import com.example.hhproject.model.Comment;
import com.example.hhproject.model.Like;
import com.example.hhproject.model.Post;
import com.example.hhproject.model.User;
import com.example.hhproject.repository.CommentRepository;
import com.example.hhproject.repository.LikeRepository;
import com.example.hhproject.repository.PostRepository;
import com.example.hhproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class LikeService {
    @Autowired
    LikeRepository likeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Transactional
    public void likePost(String userId, Long postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));

        if (likeRepository.findByUserIdAndPostId(userId, postId).isPresent()) {
            throw new RuntimeException("like already exists");
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        post.updateLikeCount(post.getLikeCount() + 1);

        likeRepository.save(like);
    }

    @Transactional
    public void unlikePost(String userId, Long postId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId).orElseThrow(() -> new NoSuchElementException("like not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("post not found"));
        post.updateLikeCount(post.getLikeCount() - 1);
        likeRepository.delete(like);
    }

    @Transactional
    public void likeComment(String userId, Long commentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("comment not found"));

        if (likeRepository.findByUserIdAndCommentId(userId, commentId).isPresent()) {
            throw new RuntimeException("like already exists");
        }

        Like like = Like.builder()
                .user(user)
                .comment(comment)
                .build();

        comment.updateLikeCount(comment.getLikeCount() + 1);

        likeRepository.save(like);
    }

    @Transactional
    public void unlikeComment(String userId, Long commentId) {
        Like like = likeRepository.findByUserIdAndCommentId(userId, commentId).orElseThrow(() -> new NoSuchElementException("like not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("comment not found"));
        comment.updateLikeCount(comment.getLikeCount() - 1);
        likeRepository.delete(like);
    }
}