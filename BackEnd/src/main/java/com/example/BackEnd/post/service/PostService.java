package com.example.BackEnd.post.service;

import com.example.BackEnd.Member.Entity.Member;
import com.example.BackEnd.Member.repository.MemberRepository;
import com.example.BackEnd.common.exception.BusinessException;
import com.example.BackEnd.count.dto.PostMetricsDto;
import com.example.BackEnd.count.repository.PostLikeCountRepository;
import com.example.BackEnd.count.repository.PostViewCountRepository;
import com.example.BackEnd.post.dto.*;
import com.example.BackEnd.post.entity.Post;
import com.example.BackEnd.post.repository.PostRepository;
import com.example.BackEnd.work.entity.Work;
import com.example.BackEnd.work.repository.WorkRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.*;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import lombok.*;

import static com.example.BackEnd.common.constants.PostConstants.*;
import static com.example.BackEnd.common.enums.error_codes.AuthErrorCode.*;
import static com.example.BackEnd.common.enums.error_codes.PostErrorCode.*;
import static com.example.BackEnd.common.enums.error_codes.WorkErrorCode.*;
import static com.example.BackEnd.count.entity.QPostLikeCount.postLikeCount;
import static com.example.BackEnd.count.entity.QPostViewCount.postViewCount;
import static com.example.BackEnd.post.entity.QPost.post;

@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final WorkRepository workRepository;
    private final PostLikeCountRepository postLikeCountRepository;
    private final PostViewCountRepository postViewCountRepository;

    @Transactional
    public Page<PostPageDto> getPosts(PostPageRequestDto postPageRequestDto) {
        int pageNumber = postPageRequestDto.getPageNumber();
        int pageSize = postPageRequestDto.getPageSize();
        String sortBy = postPageRequestDto.getSortBy();
        int offset = postPageRequestDto.getOffset();

        List<PostPageDto> posts = postRepository.getPosts(
                getPostCondition(), getOrderSpecifier(sortBy), offset, pageSize);

        if (pageNumber == 1) {
            posts = addNoticesToPosts(posts);
        }

        Pageable pageable = getPageable(pageNumber, pageSize);
        return PageableExecutionUtils.getPage(
                posts, pageable, postRepository.getCountSupplier(getPostCondition()));
    }

    private OrderSpecifier<?> getOrderSpecifier(String sortBy) {
        return switch (sortBy.toUpperCase()) {
            case "LIKE" -> postLikeCount.likeCounts.desc();
            case "VIEW" -> postViewCount.viewCounts.desc();
            default -> post.createdAt.desc();
        };
    }

    private BooleanExpression getPostCondition() {
        return post.deleted.isFalse().and(post.isNotice.isFalse());
    }

    private BooleanExpression getNoticeCondition() {
        return post.deleted.isFalse().and(post.isNotice.isTrue());
    }

    private List<PostPageDto> addNoticesToPosts(List<PostPageDto> posts) {
        List<PostPageDto> notices = postRepository.getPosts(
                getNoticeCondition(), post.createdAt.desc(), FIRST_PAGE_OFFSET, FIRST_PAGE_LIMIT);
        List<PostPageDto> totalPosts = new ArrayList<>(notices.size() + posts.size());
        totalPosts.addAll(notices);
        totalPosts.addAll(posts);
        return totalPosts;
    }

    private Pageable getPageable(int pageNumber, int pageSize) {
        return PageRequest.of(pageNumber - 1, pageSize);
    }

    @Transactional
    public PostDetailsDto createPost(PostCreationRequest postCreationRequest) {
        Member writer = findMemberByUsernameOrThrow(postCreationRequest.getUsername());
        Work work = findWorkByIdOrThrow(postCreationRequest.getWorkId());
        boolean isNotice = isNoticedByAdmin(postCreationRequest.getRoles(), postCreationRequest.isNotice());

        Post post = createPostEntity(postCreationRequest, writer, work, isNotice);
        Post savedPost = postRepository.save(post);
        return PostDetailsDto.from(savedPost, writer, work, PostMetricsDto.empty());
    }

    private Member findMemberByUsernameOrThrow(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }

    private Work findWorkByIdOrThrow(Long workId) {
        return workRepository.findById(workId)
                .orElseThrow(() -> new BusinessException(WORK_NOT_FOUND));
    }

    private boolean isNoticedByAdmin(List<String> roles, boolean isNotice) {
        return roles.contains("ROLE_ADMIN") && isNotice;
    }

    private Post createPostEntity(PostCreationRequest postCreationRequest, Member writer, Work work, boolean isNotice) {
        return Post.builder()
                .title(postCreationRequest.getTitle())
                .content(postCreationRequest.getContent())
                .writer(writer)
                .work(work)
                .isNotice(isNotice)
                .build();
    }

    @Transactional
    public PostDetailsDto updatePost(PostUpdateRequest postUpdateRequest) {
        Post existingPost = findPostByIdOrThrow(postUpdateRequest.getPostId());
        Member updater = findMemberByUsernameOrThrow(postUpdateRequest.getUsername());
        Work work = findWorkByIdOrThrow(postUpdateRequest.getWorkId());

        validateWriter(updater, existingPost.getWriter());
        boolean isNotice = isNoticedByAdmin(postUpdateRequest.getRoles(), postUpdateRequest.isNotice());
        existingPost.updatePost(postUpdateRequest.getTitle(), postUpdateRequest.getContent(), work, isNotice);

        Long viewCounts = postViewCountRepository.findViewCountByPostId(existingPost.getId()).orElse(INITIAL_VIEW_COUNTS);
        Long likeCounts = postLikeCountRepository.findLikeCountByPostId(existingPost.getId()).orElse(INITIAL_LIKE_COUNTS);
        PostMetricsDto postMetrics = PostMetricsDto.of(viewCounts, likeCounts);
        return PostDetailsDto.from(existingPost, updater, work, postMetrics);
    }

    private Post findPostByIdOrThrow(Long postId) {
        return postRepository.findPostWithWriterById(postId)
                .orElseThrow(() -> new BusinessException(POST_NOT_FOUND));
    }

    private void validateWriter(Member updater, Member writer) {
        if (updater.getId().equals(writer.getId())) {
            return;
        }
        throw new BusinessException(WRITER_MISMATCH);
    }

    @Transactional
    public void deletePost(PostDeleteRequest postDeleteRequest) {
        Post existingPost = findPostByIdOrThrow(postDeleteRequest.getPostId());
        Member deleter = findMemberByUsernameOrThrow(postDeleteRequest.getUsername());

        validateWriter(deleter, existingPost.getWriter());
        postRepository.delete(existingPost);
    }

}
