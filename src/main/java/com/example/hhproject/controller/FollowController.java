package com.example.hhproject.controller;

import com.example.hhproject.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    FollowService followService;

    @PostMapping
    public ResponseEntity<?> follow(@AuthenticationPrincipal String userId, @RequestParam("following") String following) {
        followService.addFollowing(userId, following);
        return ResponseEntity.ok().body(following + " 을 팔로우");
    }

    @GetMapping("/followings")
    public ResponseEntity<?> getFollowings(@AuthenticationPrincipal String userId) {
        List<String> followings = followService.getFollowings(userId);
        return ResponseEntity.ok().body(followings);
    }

    @GetMapping("/followers")
    public ResponseEntity<?> getFollowers(@AuthenticationPrincipal String userId) {
        List<String> followers = followService.getFollowers(userId);
        return ResponseEntity.ok().body(followers);
    }
}
