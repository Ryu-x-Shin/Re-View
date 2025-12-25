package com.example.BackEnd.post.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostPageDto {

    private Long postId;
    private String title;
    private String writer;
    private LocalDateTime createdAt;
    private Long viewCount;
    private Long likeCount;
    private Boolean isNotice;

}
