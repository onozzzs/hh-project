package com.example.hhproject.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="follow_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name="following")
    private User following;
    @ManyToOne
    @JoinColumn(name="follower")
    private User follower;
}
