package com.example.BackEnd.comment.service;

import com.example.BackEnd.comment.dto.CommentListDto;
import com.example.BackEnd.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Page<CommentListDto> getDepth1Comments(Long postId, Pageable pageable) {
        return commentRepository.findDepth1Comments(postId, pageable);
    }

    public Page<CommentListDto> getDepth2Comments(Long parentCommentId, Pageable pageable) {
        return commentRepository.findDepth2Comments(parentCommentId, pageable);
    }

}
