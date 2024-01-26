package com.example.hhproject.service;

import com.example.hhproject.model.User;
import com.example.hhproject.repository.UserRepository;
import com.example.hhproject.security.TokenProvider;
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

    private TokenProvider tokenProvider;

    public User updateUser(final String userId, User user) {
        User originalUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        originalUser.setContent(user.getContent());
        originalUser.setUsername(user.getUsername());
        return userRepository.save(originalUser);
    }

    public User updateFile(final String userId, final String fileUrl) {
        User originalUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        originalUser.setProfileUrl(fileUrl);
        return userRepository.save(originalUser);
    }

    public Boolean validatePassword(final String userId, final String oldPassword, final PasswordEncoder passwordEncoder) {
        final User originalUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        User updatedUser = getByCredentials(originalUser.getMail(), oldPassword, passwordEncoder);
        if (updatedUser == null) return false;
        return true;
    }

    public User updatePassword(final String userId, final String newPassword, PasswordEncoder passwordEncoder) {
        User originalUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("user not found"));
        User newUser = User.builder()
                .id(originalUser.getId())
                .password(passwordEncoder.encode(newPassword))
                .username(originalUser.getUsername())
                .status(originalUser.isStatus())
                .content(originalUser.getContent())
                .mail(originalUser.getMail())
                .profileUrl(originalUser.getProfileUrl())
                .build();
        return userRepository.save(originalUser);
    }

    public User getByCredentials(final String mail, final String password, final PasswordEncoder passwordEncoder) {
        final User originalUser = userRepository.findByMail(mail);
        if (originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }

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
