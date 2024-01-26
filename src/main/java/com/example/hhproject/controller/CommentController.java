package com.example.hhproject.controller;

import com.example.hhproject.dto.CommentRequestDTO;
import com.example.hhproject.model.Comment;
import com.example.hhproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/post")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}/comment")
    public ResponseEntity<?> writeComment(@AuthenticationPrincipal String userId, @PathVariable Long postId, @RequestBody CommentRequestDTO requestDTO) {
        Comment comment = Comment.builder()
                .content(requestDTO.getContent())
                .build();
        commentService.addComment(userId, postId, comment);
        return ResponseEntity.ok().body("comment is saved");
    }

}
