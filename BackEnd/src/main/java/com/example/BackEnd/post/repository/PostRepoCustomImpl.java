package com.example.BackEnd.post.repository;

import com.example.BackEnd.post.dto.PageRequestPostDto;
import com.example.BackEnd.post.dto.PostListDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;
import static com.example.BackEnd.count.entity.QPostLikeCount.postLikeCount;
import static com.example.BackEnd.count.entity.QPostViewCount.postViewCount;
import static com.example.BackEnd.post.entity.QPost.post;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostRepoCustomImpl implements PostRepoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostListDto> getPostList(PageRequestPostDto pageRequestPostDto) {
        int pageNumber = pageRequestPostDto.getPageNumber();
        int pageSize = pageRequestPostDto.getPageSize();
        String sortBy = pageRequestPostDto.getSortBy();

        // 정렬 조건
        OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortBy);

        // 일반글 조건
        BooleanExpression generalPostCondition = getGeneralPostCondition();

        // 공지글 조건
        BooleanExpression noticePostCondition = getNoticePostCondition();

        // 1. 일반 게시글(공지 제외) 페이징 조회
        List<PostListDto> posts = queryFactory
                .select(Projections.constructor(PostListDto.class,
                        post.id,
                        post.title,
                        post.writer,
                        post.createdAt,
                        postViewCount.viewCounts.coalesce(0L),
                        postLikeCount.likeCounts.coalesce(0L),
                        post.isNotice))
                .from(post)
                .leftJoin(postViewCount).on(postViewCount.postId.eq(post.id))
                .leftJoin(postLikeCount).on(postLikeCount.postId.eq(post.id))
                .where(generalPostCondition)
                .orderBy(orderSpecifier)
                .offset(pageRequestPostDto.getOffset())
                .limit(pageSize)
                .fetch();

        // 2. 1페이지면 공지글을 별도 조회
        if (pageNumber == 1) {
            List<PostListDto> noticePosts = queryFactory
                    .select(Projections.constructor(PostListDto.class,
                            post.id,
                            post.title,
                            post.writer,
                            post.createdAt,
                            postViewCount.viewCounts.coalesce(0L),
                            postLikeCount.likeCounts.coalesce(0L),
                            post.isNotice))
                    .from(post)
                    .leftJoin(postViewCount).on(postViewCount.postId.eq(post.id))
                    .leftJoin(postLikeCount).on(postLikeCount.postId.eq(post.id))
                    .where(noticePostCondition)
                    .orderBy(post.createdAt.desc())
                    .fetch();

            List<PostListDto> combined = new ArrayList<>(noticePosts.size() + posts.size());
            combined.addAll(noticePosts);
            combined.addAll(posts);
            posts = combined;
        }

        // 3. 블록 활성화용 제한 count
        int pageBlocks = 10;
        int limitCount = (((pageNumber - 1) / pageBlocks) + 1) * pageSize * pageBlocks + 1;

        // 4. Page 생성
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return PageableExecutionUtils.getPage(posts, pageable, getCountSupplier(limitCount));
    }

    private static BooleanExpression getNoticePostCondition() {
        return post.deleted.isFalse().and(post.isNotice.isTrue());
    }

    private static BooleanExpression getGeneralPostCondition() {
        return post.deleted.isFalse().and(post.isNotice.isFalse());
    }

    private LongSupplier getCountSupplier(int limitCount) {
        return () -> Optional.ofNullable(
                queryFactory.select(post.count())
                        .from(post)
                        .where(getGeneralPostCondition())
                        .limit(limitCount)  // 블록 단위 최적화
                        .fetchFirst()
        ).orElse(0L);
    }

    private static OrderSpecifier<?> getOrderSpecifier(String sortBy) {
        return switch (sortBy.toUpperCase()) {
            case "LIKE" -> postLikeCount.likeCounts.desc();
            case "VIEW" -> postViewCount.viewCounts.desc();
            default -> post.createdAt.desc();
        };
    }

}
