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
@Table(name = "post_views_counts")
public class ViewCount {

    @Id
    @Column(name = "post_id")
    private Long postId;

    @Builder.Default
    private Long viewCounts = 0L;

}
