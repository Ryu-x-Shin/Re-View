package com.example.BackEnd.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostLikeEvent {

    private final Long postId;

    private final Long memberId;

}
