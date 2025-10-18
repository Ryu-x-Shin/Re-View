package com.example.BackEnd.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostListDto {

    private Long postId;

    private String title;

    private String writer;

    private LocalDateTime createdAt;

    private Long viewCount;

    private Long likeCount;

    private Boolean isNotice;

}
