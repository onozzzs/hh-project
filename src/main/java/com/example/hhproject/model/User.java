package com.example.hhproject.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.Nullable;

import java.util.List;

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

    @Nullable
    @OneToMany(mappedBy = "follower")
    private List<Follow> followingList;

    @Nullable
    @OneToMany(mappedBy = "following")
    private List<Follow> followerList;
}
