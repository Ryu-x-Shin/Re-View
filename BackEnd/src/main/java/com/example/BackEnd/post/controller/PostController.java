package com.example.BackEnd.post.controller;

import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.post.dto.*;
import com.example.BackEnd.post.service.PostService;
import com.example.BackEnd.security.auth.dto.CustomMemberPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.*;

import static com.example.BackEnd.common.Response.ApiResponse.*;
import static com.example.BackEnd.common.constants.PostConstants.*;
import static com.example.BackEnd.common.enums.error_codes.PostErrorCode.*;
import static com.example.BackEnd.common.enums.success_messages.PostSuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int page,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int size,
                                      @RequestParam(defaultValue = DEFAULT_PAGE_SORTBY) String sortBy) {
        validatePageNumberInRange(page);
        validatePageSizeInRange(size);

        PostPageRequestDto postPageRequestDto = createPostPageRequest(page, size, sortBy);
        Page<PostPageDto> posts = postService.getPosts(postPageRequestDto);

        return success(POST_LIST_FETCH_SUCCESS, posts);
    }

    private void validatePageNumberInRange(int page) {
        // 정책으로 최대 페이지 제한
        if (page < MIN_PAGE_NUMBER || page > MAX_PAGE_NUMBER) {
            throw new BusinessException(POST_BAD_REQUEST);
        }
    }

    private void validatePageSizeInRange(int size) {
        if (size < MIN_PAGE_SIZE || size > MAX_PAGE_SIZE) {
            throw new BusinessException(POST_BAD_REQUEST);
        }
    }

    private PostPageRequestDto createPostPageRequest(int page, int size, String sortBy) {
        return PostPageRequestDto.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
    public ResponseEntity<?> createPost(@RequestBody @Valid PostCreationRequest postCreationRequest,
                                        @AuthenticationPrincipal CustomMemberPrincipal principal) {
        PostCreationRequest postCreationRequestWithAuth = addAuthToPostCreationRequest(postCreationRequest, principal);
        PostDetailsDto postDetails = postService.createPost(postCreationRequestWithAuth);
        return success(POST_CREATED, postDetails);
    }

    private PostCreationRequest addAuthToPostCreationRequest(PostCreationRequest postCreationRequest,
                                                           CustomMemberPrincipal principal) {
        return postCreationRequest.toBuilder()
                .username(principal.getName())
                .roles(principal.getRoles())
                .build();
    }

    @PutMapping("/{postId}")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
    public ResponseEntity<?> updatePost(@PathVariable Long postId,
                                        @RequestBody @Valid PostUpdateRequest postUpdateRequest,
                                        @AuthenticationPrincipal CustomMemberPrincipal principal) {
        PostUpdateRequest postUpdateRequestWithAuth = addAuthToPostUpdateRequest(postUpdateRequest, principal, postId);
        PostDetailsDto updatedPostDetails = postService.updatePost(postUpdateRequestWithAuth);
        return success(POST_UPDATED, updatedPostDetails);
    }

    private PostUpdateRequest addAuthToPostUpdateRequest(PostUpdateRequest postUpdateRequest,
                                                         CustomMemberPrincipal principal,
                                                         Long postId) {
        return postUpdateRequest.toBuilder()
                .username(principal.getName())
                .roles(principal.getRoles())
                .postId(postId)
                .build();
    }

    @DeleteMapping("/{postId}")
    @PreAuthorize("hasAnyRole('MEMBER','ADMIN')")
    public ResponseEntity<?> deletePost(@PathVariable Long postId,
                                        @AuthenticationPrincipal CustomMemberPrincipal principal) {
        PostDeleteRequest postDeleteRequest = createPostDeleteRequest(principal, postId);
        postService.deletePost(postDeleteRequest);
        return success(POST_DELETED);
    }

    private PostDeleteRequest createPostDeleteRequest(CustomMemberPrincipal principal, Long postId) {
        return PostDeleteRequest.builder()
                .username(principal.getName())
                .roles(principal.getRoles())
                .postId(postId)
                .build();
    }

}
