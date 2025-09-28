package com.example.BackEnd.count.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comment_likes_counts")
public class CommentLikeCount {

    @Id
    @Column(name = "comment_id")
    private Long commentId;

    @Builder.Default
    private Long likeCounts = 0L;

}
