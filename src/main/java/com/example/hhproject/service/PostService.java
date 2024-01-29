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

        List<Post> updatedPosts = addPost(posts, followings);
        return sortPostByDesc(updatedPosts);
    }

    public List<Post> getFollowerPost(final String userId) {
        List<String> followers = followService.getFollowers(userId);
        List<Post> posts = new ArrayList<>();

        List<Post> updatedPosts = addPost(posts, followers);
        return sortPostByDesc(updatedPosts);
    }

    private List<Post> addPost(List<Post> posts, List<String> targets) {
        List<Post> results = posts;
        for (String name : targets) {
            User target = userRepository.findByUsername(name);
            List<Post> targetPosts = postRepository.findByWriterId(target.getId());
            for (Post post : targetPosts) {
                results.add(post);
            }
        }
        return results;
    }

    private List<Post> sortPostByDesc(List<Post> posts) {
        List<Post> sortedPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return sortedPosts;
    }
}
