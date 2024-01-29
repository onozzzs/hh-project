package com.example.hhproject.service;

import com.example.hhproject.model.User;
import com.example.hhproject.repository.UserRepository;
import com.example.hhproject.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.Random;

@Slf4j
@Service
public class MailService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;
    private int authNumber;

    @Transactional
    public User checkMail(String mail, String auth) {
        if (redisUtil.getData(auth).equals(mail)) {
            User user = userRepository.findByMail(mail);
            user.updateStatus();
            return user;
        }
        return null;
    }

    public String makeAndSendMail(String mail) {
        if (mail == null) {
            log.warn("email cannot be null");
            throw new RuntimeException("email cannot be null");
        }
        makeRandomNumber();
        String from = "test12321421@gmail.com";
        String to = mail;
        String title = "회원가입 인증 이메일";
        String content = "인증번호는 " + authNumber + " 입니다";
        sendMail(from, to, title, content);
        return Integer.toString(authNumber);
    }

    private void sendMail(String from, String to, String title, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(title);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        redisUtil.setDataExpire(Integer.toString(authNumber), to, 60 * 5L);
    }

    private void makeRandomNumber() {
        Random r = new Random();
        String randomNumber = "";
        for (int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }
        authNumber = Integer.parseInt(randomNumber);
    }
}
