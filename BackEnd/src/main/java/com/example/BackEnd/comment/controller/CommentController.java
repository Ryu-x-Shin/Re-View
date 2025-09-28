package com.example.BackEnd.comment.controller;

import com.example.BackEnd.comment.dto.CommentListDto;
import com.example.BackEnd.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Page<CommentListDto> getDepth1Comments(
            @PathVariable Long postId,
            Pageable pageable) {
        return commentService.getDepth1Comments(postId, pageable);
    }

    @GetMapping("/{parentCommentId}/reply")
    public Page<CommentListDto> getDepth2Comments(
            @PathVariable Long parentCommentId,
            Pageable pageable) {
        return commentService.getDepth2Comments(parentCommentId, pageable);
    }

}
