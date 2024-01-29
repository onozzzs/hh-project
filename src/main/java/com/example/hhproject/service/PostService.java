package com.example.hhproject.service;

import com.example.hhproject.model.Category;
import com.example.hhproject.model.Post;
import com.example.hhproject.model.User;
import com.example.hhproject.repository.PostRepository;
import com.example.hhproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FollowService followService;

    @Autowired
    private PostActivityServiceImpl activityService;

    public void savePost(final String userId, Post post) {
        User writer = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        post.setWriter(writer);
        postRepository.save(post);

        activityService.makeAndSaveActivity(writer, Category.POST, String.valueOf(post.getId()));
    }

    public List<Post> getPost(final String userId) {
        List<String> followings = followService.getFollowings(userId);
        List<Post> posts = postRepository.findByWriterId(userId);

        for (String followingUserName : followings) {
            User followingUser = userRepository.findByUsername(followingUserName);
            List<Post> followingPosts = postRepository.findByWriterId(followingUser.getId());
            for (Post post : followingPosts) {
                posts.add(post);
            }
        }
        return sortPostByDesc(posts);
    }

    public List<Post> getFollowerPost(final String userId) {
        List<String> followers = followService.getFollowers(userId);
        List<Post> posts = new ArrayList<>();

        for (String followerName : followers) {
            User follower = userRepository.findByUsername(followerName);
            List<Post> followerPosts = postRepository.findByWriterId(follower.getId());
            for (Post post : followerPosts) {
                posts.add(post);
            }
        }

        return sortPostByDesc(posts);
    }

    private List<Post> sortPostByDesc(List<Post> posts) {
        List<Post> sortedPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return sortedPosts;
    }
}
