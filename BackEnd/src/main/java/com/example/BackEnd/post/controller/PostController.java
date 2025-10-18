package com.example.BackEnd.post.controller;

import com.example.BackEnd.common.Response.ApiResponse;
import com.example.BackEnd.common.enums.error_codes.PostError;
import com.example.BackEnd.common.enums.success_messages.PostSuccess;
import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.post.dto.PageRequestPostDto;
import com.example.BackEnd.post.dto.PostListDto;
import com.example.BackEnd.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public ResponseEntity<?> getPostList(@RequestParam(defaultValue = "1") int page,
                                         @RequestParam(defaultValue = "15") int size,
                                         @RequestParam(defaultValue = "RECENT") String sortBy) {

        if (page < 1 || page > 10000) {
            throw new BusinessException(PostError.BAD_REQUEST);
        }
        if (size < 10 || size > 50) {
            throw new BusinessException(PostError.BAD_REQUEST);
        }

        PageRequestPostDto pageRequestPostDto = PageRequestPostDto.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();

        Page<PostListDto> postList = postService.getPostList(pageRequestPostDto);

        return ApiResponse.success(PostSuccess.POST_LIST_FETCH_SUCCESS, postList);
    }

}
