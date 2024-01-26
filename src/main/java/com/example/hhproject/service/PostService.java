package com.example.hhproject.service;

import com.example.hhproject.model.Post;
import com.example.hhproject.model.User;
import com.example.hhproject.repository.PostRepository;
import com.example.hhproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void savePost(final String userId, Post post) {
        User writer = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        post.setWriter(writer);
        postRepository.save(post);
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

        List<Post> sortedPosts = posts.stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());

        return sortedPosts;
    }
}
