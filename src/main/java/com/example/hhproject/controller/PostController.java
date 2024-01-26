package com.example.hhproject.controller;

import com.example.hhproject.dto.PostRequestDTO;
import com.example.hhproject.dto.PostResponseDTO;
import com.example.hhproject.model.Post;
import com.example.hhproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> savePost(@AuthenticationPrincipal String userId, @RequestBody PostRequestDTO requestDTO) {
        Post post = Post.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .build();
        postService.savePost(userId, post);
        return ResponseEntity.ok().body("post is saved");
    }

    @GetMapping
    public ResponseEntity<?> getPost(@AuthenticationPrincipal String userId) {
        List<Post> posts = postService.getPost(userId);
        List<PostResponseDTO> responseDTOs = posts.stream().map(PostResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(responseDTOs);
    }
}
