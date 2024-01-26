package com.example.hhproject.service;

import com.example.hhproject.model.Follow;
import com.example.hhproject.model.User;
import com.example.hhproject.repository.FollowRepository;
import com.example.hhproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    public List<String> getFollowings(final String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        List<String> followings = new ArrayList<>();
        for (Follow follow : user.getFollowingList()) {
            followings.add(follow.getFollowing().getUsername());
        }
        return followings;
    }

    public List<String> getFollowers(final String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        List<String> followers = new ArrayList<>();
        for (Follow follow : user.getFollowerList()) {
            followers.add(follow.getFollower().getUsername());
        }
        return followers;
    }

    public void addFollowing(final String userId, final String followingName) {
        User follower = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        validate(follower);

        User following = userRepository.findByUsername(followingName);
        validate(following);

        Optional<Follow> follow = followRepository.findByFollowerIdAndFollowingId(userId, following.getId());
        if (follow.isPresent()) {
            throw new RuntimeException("addFollowing " + "follow already exists");
        }

        Follow newFollow = Follow.builder()
                .following(following)
                .follower(follower)
                .build();

        followRepository.save(newFollow);
    }

    private void validate(User user) {
        if (user == null) {
            log.error("follingUser cannot be null");
            throw new RuntimeException("addFollow: following is null");
        }
    }
}
