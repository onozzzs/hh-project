package com.example.hhproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.Nullable;

@Builder
@NoArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @UuidGenerator
    private String id;
    private String email;
    private String password;
    private String username;

    @Nullable
    private String content;

    @Nullable
    private String profile_img;

    @Nullable
    private boolean is_valid;
}
