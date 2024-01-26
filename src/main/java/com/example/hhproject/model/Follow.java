package com.example.hhproject.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @ManyToOne
    @JoinColumn(name="following_id")
    public User following;
    @ManyToOne
    @JoinColumn(name="follower_id")
    public User follower;
}
