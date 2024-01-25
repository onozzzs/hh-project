package com.example.hhproject.repository;

import com.example.hhproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);
    User findByMail(String mail);
    Boolean existsByUsername(String username);
    Boolean existsByMail(String mail);
}
