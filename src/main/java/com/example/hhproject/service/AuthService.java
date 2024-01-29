package com.example.hhproject.service;

import com.example.hhproject.model.User;
import com.example.hhproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public User getByCredentials(final String mail, final String password, final PasswordEncoder passwordEncoder) {
        final User originalUser = userRepository.findByMail(mail);
        if (originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }

    public User signup(final User user) {
        final String username = user.getUsername();
        final String mail = user.getMail();
        if (userRepository.existsByUsername(username) || userRepository.existsByMail(mail)) {
            throw new RuntimeException("User already exists");
        }

        return userRepository.save(user);
    }
}
