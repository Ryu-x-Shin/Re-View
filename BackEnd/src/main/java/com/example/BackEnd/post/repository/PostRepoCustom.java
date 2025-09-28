package com.example.BackEnd.post.repository;

import com.example.BackEnd.post.dto.PostListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepoCustom {

    Page<PostListDto> findPostsForList(String sortBy, Pageable pageable);

}
