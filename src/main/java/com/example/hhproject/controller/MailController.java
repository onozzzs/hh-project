package com.example.hhproject.controller;

import com.example.hhproject.dto.UserDTO;
import com.example.hhproject.model.User;
import com.example.hhproject.service.AuthService;
import com.example.hhproject.service.MailService;
import com.example.hhproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthService authService;

    @PostMapping("/send")
    public String sendMail(@RequestParam("mail") String mail) {
        return mailService.makeAndSendMail(mail);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyMail(@RequestParam("mail") String mail,
                                        @RequestParam("auth") String auth) {
        User changedUser = mailService.checkMail(mail, auth);
        UserDTO responseUserDTO = new UserDTO(changedUser.getId(), changedUser.getMail(), changedUser.getPassword(), changedUser.isStatus());
        return ResponseEntity.ok().body(responseUserDTO);
    }
}
