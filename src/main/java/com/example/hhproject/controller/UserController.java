package com.example.hhproject.controller;

import com.example.hhproject.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final MailService mailService;

    @PostMapping("/signup/mailConfirm")
    public String sendMail(@RequestParam("mail") String mail) {
        return mailService.makeAndSendMail(mail);
    }
}
