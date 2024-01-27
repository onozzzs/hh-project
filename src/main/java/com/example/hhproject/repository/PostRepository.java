package com.example.hhproject.repository;

import com.example.hhproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findByWriterId(String writer);
    Optional<Post> findById(Long postId);
}
