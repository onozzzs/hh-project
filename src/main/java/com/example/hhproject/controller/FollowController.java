package com.example.hhproject.controller;

import com.example.hhproject.model.Follow;
import com.example.hhproject.service.FollowService;
import org.apache.coyote.Response;
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

    @GetMapping("/following")
    public ResponseEntity<?> getFollowingList(@AuthenticationPrincipal String userId) {
        List<String> followings = followService.getFollowings(userId);
        return ResponseEntity.ok().body(followings);
    }

    @GetMapping("/follower")
    public ResponseEntity<?> getFollowerList(@AuthenticationPrincipal String userId) {
        List<String> followers = followService.getFollowers(userId);
        return ResponseEntity.ok().body(followers);
    }
}
