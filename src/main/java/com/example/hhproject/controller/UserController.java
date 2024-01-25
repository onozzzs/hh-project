package com.example.hhproject.controller;

import com.example.hhproject.dto.UserDTO;
import com.example.hhproject.model.User;
import com.example.hhproject.security.TokenProvider;
import com.example.hhproject.service.MailService;
import com.example.hhproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final MailService mailService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserDTO userDTO) {
        User user = userService.getByCredentials(
                userDTO.getMail(),
                userDTO.getPassword()
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

            User user = UserDTO.toEntity(userDTO);
            user.setStatus(false);

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
