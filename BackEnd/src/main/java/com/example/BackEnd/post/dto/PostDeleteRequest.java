package com.example.BackEnd.post.dto;

import lombok.*;
import java.util.*;

@Getter
@Builder
@AllArgsConstructor
public class PostDeleteRequest {

    private String username;

    private List<String> roles;

    private Long postId;

}
