package com.example.BackEnd.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentLikeEvent {

    private final Long commentId;

    private final Long memberId;

}
