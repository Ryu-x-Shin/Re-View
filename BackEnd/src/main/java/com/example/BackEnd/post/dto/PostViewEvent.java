package com.example.BackEnd.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostViewEvent {

    private final Long postId;

    private final String viewer;

}
