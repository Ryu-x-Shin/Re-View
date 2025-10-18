package com.example.BackEnd.post.entity;

import com.example.BackEnd.Member.Entity.Member;
import com.example.BackEnd.work.entity.Work;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private String content;

    @Builder.Default
    private Boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private Work work;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

    private Boolean isNotice;

    public void updatePost(String title, Work work, String content) {
        this.title = title;
        this.work = work;
        this.content = content;
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void softDelete() {
        this.deleted = true;
    }

}
