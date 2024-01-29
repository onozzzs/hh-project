package com.example.hhproject.controller;

import com.example.hhproject.dto.*;
import com.example.hhproject.model.User;
import com.example.hhproject.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private S3UploadService uploadService;
    @Autowired
    private UserService userService;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal String userId, @RequestBody PasswordDTO passwordDTO) {
        if (passwordDTO == null) {
            throw new RuntimeException("Empty password");
        }
        Boolean isValid = userService.validatePassword(userId, passwordDTO.getOldPassword(), passwordEncoder);
        if (isValid) {
            User updatedUser = userService.updatePassword(userId, passwordDTO.getNewPassword(), passwordEncoder);
            UserDTO responseUserDTO = new UserDTO(updatedUser.getId(), updatedUser.getMail(), updatedUser.getPassword(), updatedUser.isStatus());
            return ResponseEntity.ok().body(responseUserDTO);
        }
        return ResponseEntity.badRequest().body("Failed to update password");
    }

    @PostMapping("/updateFile")
    public ResponseEntity<?> updateFile(@AuthenticationPrincipal String userId, @RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = uploadService.saveFile(file);
        User user = userService.updateFile(userId, fileUrl);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal String userId, @RequestBody UserDTO userDTO) {
        User user = UserDTO.toEntity(userDTO);
        User updatedUser = userService.updateUser(userId, user);
        UserDTO responseUserDTO = UserDTO.builder()
                .username(updatedUser.getUsername())
                .mail(updatedUser.getMail())
                .password(updatedUser.getPassword())
                .content(updatedUser.getContent())
                .build();

        return ResponseEntity.ok().body(responseUserDTO);
    }
}
