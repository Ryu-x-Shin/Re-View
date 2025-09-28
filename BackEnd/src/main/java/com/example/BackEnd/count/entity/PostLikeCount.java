package com.example.BackEnd.count.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post_likes_counts")
public class PostLikeCount {

    @Id
    @Column(name = "post_id")
    private Long postId;

    @Builder.Default
    private Long likeCounts = 0L;

}
