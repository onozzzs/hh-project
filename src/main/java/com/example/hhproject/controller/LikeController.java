package com.example.hhproject.controller;

import com.example.hhproject.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/like")
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping("/post/{postId}")
    public ResponseEntity<?> likePost(@AuthenticationPrincipal String userId, @PathVariable Long postId) {
        likeService.likePost(userId, postId);
        return ResponseEntity.ok().body("likes on post " + postId);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> unlikePost(@AuthenticationPrincipal String userId, @PathVariable Long postId) {
        likeService.unlikePost(userId, postId);
        return ResponseEntity.ok().body("unlikes on post " + postId);
    }

    @PostMapping("/comment/{commentId}")
    public ResponseEntity<?> likeComment(@AuthenticationPrincipal String userId, @PathVariable Long commentId) {
        likeService.likeComment(userId, commentId);
        return ResponseEntity.ok().body("likes on comment " + commentId);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> unlikeComment(@AuthenticationPrincipal String userId, @PathVariable Long commentId) {
        likeService.unlikeComment(userId, commentId);
        return ResponseEntity.ok().body("unlikes on comment " + commentId);
    }
}
