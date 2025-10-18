package com.example.BackEnd.post.repository;

import com.example.BackEnd.post.dto.PageRequestPostDto;
import com.example.BackEnd.post.dto.PostListDto;
import org.springframework.data.domain.Page;

public interface PostRepoCustom {

    Page<PostListDto> getPostList(PageRequestPostDto pageRequestPostDto);

}
