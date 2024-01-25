package com.example.hhproject.controller;

import com.example.hhproject.dto.PasswordDTO;
import com.example.hhproject.dto.UserDTO;
import com.example.hhproject.model.User;
import com.example.hhproject.security.TokenProvider;
import com.example.hhproject.service.MailService;
import com.example.hhproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final MailService mailService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal String userId, @RequestBody PasswordDTO passwordDTO) {
        if (passwordDTO == null) {
            throw new RuntimeException("Empty password");
        }
        // 1. userId 로 찾은 비밀 번호와 passwordDTO.getOldPassword() 일치하는지 확인
        Boolean isValid = userService.validatePassword(userId, passwordDTO.getOldPassword(), passwordEncoder);
        // 2. 일치한다면 비밀번호 업데이트
        if (isValid) {
            User updatedUser = userService.updatePassword(userId, passwordDTO.getNewPassword(), passwordEncoder);
            UserDTO responseUserDTO = UserDTO.builder()
                    .mail(updatedUser.getMail())
                    .password(updatedUser.getPassword())
                    .id(updatedUser.getId())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }
        return ResponseEntity.badRequest().body("Failed to update password");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
        User user = userService.getByCredentials(
                userDTO.getMail(),
                userDTO.getPassword(),
                passwordEncoder
        );

        if (user != null) {
            final String token = tokenProvider.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .mail(user.getMail())
                    .password(user.getPassword())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            return ResponseEntity.badRequest().body("Login Failed");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO == null) {
                throw new RuntimeException("Invalid user value");
            }

            User user = User.builder()
                    .mail(userDTO.getMail())
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .status(false)
                    .build();

            User registeredUser = userService.registerUser(user);
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

    @PostMapping("/signup/sendMail")
    public String sendMail(@RequestParam("mail") String mail) {
        return mailService.makeAndSendMail(mail);
    }

    @PostMapping("/signup/verifyMail")
    public ResponseEntity<?> verifyMail(@RequestParam("mail") String mail,
                             @RequestParam("auth") String auth) {
        Boolean is_valid = mailService.checkMail(mail, auth);

        if (is_valid) {
            User changedUser = userService.changeStatus(mail);
            return ResponseEntity.ok().body(changedUser);
        } else {
            return ResponseEntity.ok().body("wrong code");
        }
    }
}
