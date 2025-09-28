package com.example.BackEnd.comment.repository;

import com.example.BackEnd.comment.dto.CommentListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepoCustom {

    Page<CommentListDto> findDepth1Comments(Long postId, Pageable pageable);
    Page<CommentListDto> findDepth2Comments(Long parentCommentId, Pageable pageable);

}
