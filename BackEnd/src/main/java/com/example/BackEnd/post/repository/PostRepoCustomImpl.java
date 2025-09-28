package com.example.BackEnd.post.repository;

import com.example.BackEnd.post.dto.PostListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import static com.example.BackEnd.count.entity.QCommentCount.commentCount;
import static com.example.BackEnd.count.entity.QPostLikeCount.postLikeCount;
import static com.example.BackEnd.count.entity.QViewCount.viewCount;
import static com.example.BackEnd.post.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepoCustomImpl implements PostRepoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostListDto> findPostsForList(String sortBy, Pageable pageable) {

        JPAQuery<PostListDto> query = queryFactory
                .select(Projections.constructor(
                        PostListDto.class,
                        post.id,    // 게시물 번호
                        post.title, // 제목
                        post.writer.nickname, // 작성자 닉네임
                        post.createdAt, // 작성 시간
                        viewCount.viewCounts.coalesce(0L),  // 조회 수
                        postLikeCount.likeCounts.coalesce(0L),  // 좋아요 수
                        commentCount.commentCounts.coalesce(0L) // 댓글 수
                ))
                .from(post)
                .leftJoin(viewCount).on(post.id.eq(viewCount.postId))
                .leftJoin(postLikeCount).on(post.id.eq(postLikeCount.postId))
                .leftJoin(commentCount).on(post.id.eq(commentCount.postId))
                .where(post.deleted.isFalse());

        // 정렬 기준
        switch (sortBy) {
            case "views":
                query.orderBy(viewCount.viewCounts.desc());
                break;
            case "likes":
                query.orderBy(postLikeCount.likeCounts.desc());
                break;
            case "comments":
                query.orderBy(commentCount.commentCounts.desc());
                break;
            default: // 기본 최신순
                query.orderBy(post.createdAt.desc());
        }

        List<PostListDto> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count Query 최적화
        LongSupplier countSupplier = getPostListSupplier(pageable);

        return PageableExecutionUtils.getPage(content, pageable, countSupplier);

    }

    private LongSupplier getPostListSupplier(Pageable pageable) {

        // 페이지 번호 활성화
        int n = pageable.getPageNumber() + 1;
        int m = pageable.getPageSize();
        int limitCount = (((n - 1) / 10) + 1) * m * 10 + 1;

        return () ->
                Optional.ofNullable(queryFactory
                        .select(post.count())
                        .from(post)
                        .where(post.deleted.isFalse())
                        .limit(limitCount)
                        .fetchOne()).orElse(0L);
    }

}
