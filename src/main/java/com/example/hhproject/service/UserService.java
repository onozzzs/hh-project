package com.example.hhproject.service;

import com.example.hhproject.model.User;
import com.example.hhproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(final User user) {
        final String username = user.getUsername();
        final String mail = user.getMail();
        if (userRepository.existsByUsername(username) || userRepository.existsByMail(mail)) {
            throw new RuntimeException("User already exists");
        }
        return userRepository.save(user);
    }

    public User changeStatus(String mail) {
        if (!userRepository.existsByMail(mail)) {
            throw new RuntimeException("wrong email");
        }
        User user = userRepository.findByMail(mail);
        user.setStatus(true);
        userRepository.save(user);
        return userRepository.findByMail(mail);
    }
}
