package com.example.BackEnd.post.entity;

import com.example.BackEnd.Member.Entity.Member;
import com.example.BackEnd.common.entity.BaseTimeEntity;
import com.example.BackEnd.work.entity.Work;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
@SQLDelete(sql = "update posts set deleted = true where post_id = ?")
@SQLRestriction("deleted = false")
public class Post extends BaseTimeEntity {

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
    private boolean deleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_id")
    private Work work;

    @Builder.Default
    private boolean isNotice = false;

    public void updatePost(String title, String content, Work work, boolean isNotice) {
        this.title = title;
        this.content = content;
        this.work = work;
        this.isNotice = isNotice;
    }

}
