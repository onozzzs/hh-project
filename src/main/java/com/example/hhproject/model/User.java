package com.example.hhproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.Nullable;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @UuidGenerator
    private String id;
    private String mail;
    private String password;
    private String username;

    @Nullable
    private String content;

    @Nullable
    private String profileUrl;

    @Nullable
    private boolean status;
}
