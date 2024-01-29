package com.example.hhproject.repository;

import com.example.hhproject.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPostId(String userId, Long postId);
    Optional<Like> findByUserIdAndCommentId(String userId, Long commentId);
}
