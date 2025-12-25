package com.example.BackEnd.post.dto;

import com.example.BackEnd.Member.Entity.Member;
import com.example.BackEnd.count.dto.PostMetricsDto;
import com.example.BackEnd.post.entity.Post;
import com.example.BackEnd.work.entity.Work;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailsDto {

    private String title;
    private String content;
    private Long workId;
    private String workTitle;
    private boolean isNotice;
    private String writerNickname;
    private String writerProfileImage;
    private LocalDateTime createdAt;
    private Long viewCounts;
    private Long likeCounts;

    public static PostDetailsDto from(Post post, Member writer, Work work, PostMetricsDto postMetricsDto) {
        return PostDetailsDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .workId(work.getId())
                .workTitle(work.getTitle())
                .isNotice(post.isNotice())
                .writerNickname(writer.getNickname())
                .writerProfileImage(writer.getProfileImageUrl())
                .createdAt(post.getCreatedAt())
                .viewCounts(postMetricsDto.getViewCounts())
                .likeCounts(postMetricsDto.getLikeCounts())
                .build();
    }

}
