package com.example.BackEnd.count.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_views_counts")
public class PostViewCount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "post_id", unique = true)
    private Long postId;

    @Builder.Default
    private Long viewCounts = 0L;

}
