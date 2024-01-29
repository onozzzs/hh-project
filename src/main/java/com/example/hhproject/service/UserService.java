package com.example.hhproject.service;

import com.example.hhproject.model.User;
import com.example.hhproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @Transactional
    public User updatePassword(final String userId, final String newPassword, PasswordEncoder passwordEncoder) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        user.updatePassword(passwordEncoder.encode(newPassword));
        return user;
    }

    public Boolean validatePassword(final String userId, final String oldPassword, final PasswordEncoder passwordEncoder) {
        final User originalUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        User updatedUser = authService.getByCredentials(originalUser.getMail(), oldPassword, passwordEncoder);
        if (updatedUser == null) return false;
        return true;
    }

    @Transactional
    public User updateFile(final String userId, final String fileUrl) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        user.updateProfileUrl(fileUrl);
        return user;
    }

    @Transactional
    public User updateUser(final String userId, User user) {
        User originalUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        originalUser.updateUsernameAndContent(user.getUsername(), user.getContent());
        return originalUser;
    }
}
