package com.example.hhproject.security;

import com.example.hhproject.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String create(User user) {
        Date expiryDate = Date.from(
                Instant.now()
                .plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(user.getId())
                .setIssuer("hhproject-app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder().setSigningKey(SECRET_KEY)
                .build().parseClaimsJws(accessToken).getBody().getExpiration();

        long now = new Date().getTime();

        return (expiration.getTime() - now);
    }
}
