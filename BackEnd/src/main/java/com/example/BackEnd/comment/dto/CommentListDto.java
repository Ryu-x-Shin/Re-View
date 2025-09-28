package com.example.BackEnd.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentListDto {

    private final Long id;

    private final String nickName;

    private final String profileImageUrl;

    private final String content;

    private final LocalDateTime createdAt;

    private final Long likeCounts;

    private final Integer depth;

}
