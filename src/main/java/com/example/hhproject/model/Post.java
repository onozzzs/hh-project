package com.example.hhproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "writer")
    private User writer;

    private String title;

    private String content;

    @ColumnDefault("0")
    private int likeCount;

    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
