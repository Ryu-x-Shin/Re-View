package com.example.BackEnd.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostListDto {

    private final Long id;

    private final String title;

    private final String nickName;

    private final LocalDateTime createdAt;

    private final Long viewCounts;

    private final Long likeCounts;

    private final Long commentCounts;

}
