package com.example.hhproject.controller;

import com.example.hhproject.dto.TokenRequestDTO;
import com.example.hhproject.dto.UserDTO;
import com.example.hhproject.model.User;
import com.example.hhproject.security.TokenProvider;
import com.example.hhproject.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO == null) {
                throw new RuntimeException("Invalid user value");
            }

            User user = User.builder()
                    .mail(userDTO.getMail())
                    .password(passwordEncoder.encode((userDTO.getPassword())))
                    .username(userDTO.getUsername())
                    .build();

            User registeredUser = authService.signup(user);

            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .mail(registeredUser.getMail())
                    .username(registeredUser.getUsername())
                    .password(registeredUser.getPassword())
                    .status(registeredUser.isStatus())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        User user = authService.getByCredentials(
                userDTO.getMail(),
                userDTO.getPassword(),
                passwordEncoder
        );

        if (user != null) {
            final String token = tokenProvider.create(user);

            redisTemplate.opsForValue().set("JWT_TOKEN:" + user.getId(), token, tokenProvider.getExpiration(token));

            UserDTO responseUserDTO = UserDTO.builder()
                    .mail(user.getMail())
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .id(user.getId())
                    .token(token)
                    .status(user.isStatus())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            return ResponseEntity.badRequest().body("Login Failed");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal String userId, @RequestBody TokenRequestDTO tokenRequestDTO) {
        if (redisTemplate.opsForValue().get("JWT_TOKEN:" + userId) != null) {
            redisTemplate.delete("JWT_TOKEN:" + userId);
        }
        return ResponseEntity.ok().body("Logout successful");
    }
}
