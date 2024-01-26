package com.example.hhproject.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @UuidGenerator
    @Column(name="user_id")
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

    @Nullable
    @OneToMany(mappedBy = "receiver")
    private List<Notification> notificationList;
}
