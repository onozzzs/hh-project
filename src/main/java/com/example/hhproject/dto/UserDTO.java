package com.example.hhproject.dto;

import com.example.hhproject.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {
    private String token;
    private String mail;
    private String username;
    private String password;
    private String id;
    private boolean status;

    public static User toEntity(final UserDTO dto) {
        return User.builder()
                .mail(dto.getMail())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();
    }
}
