package com.example.BackEnd.post.dto;

import jakarta.validation.constraints.*;
import java.util.List;
import lombok.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class PostUpdateRequest {

    @NotBlank(message = "{post.title.notblank}")
    @Size(max = 500, message = "{post.title.size}")
    private String title;

    @NotBlank(message = "{post.content.notblank}")
    private String content;

    @NotBlank(message = "{post.work.notblank}")
    private Long workId;

    @NotBlank(message = "{post.isNotice.notblank}")
    private boolean isNotice;

    private String username;

    private List<String> roles;

    private Long postId;

}
