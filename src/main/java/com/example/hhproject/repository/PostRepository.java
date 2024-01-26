package com.example.hhproject.repository;

import com.example.hhproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{
    List<Post> findByWriterId(String writer);
}
