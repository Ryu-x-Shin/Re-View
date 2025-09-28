package com.example.BackEnd.comment.repository;

import com.example.BackEnd.comment.dto.CommentListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import static com.example.BackEnd.Member.Entity.QMember.member;
import static com.example.BackEnd.comment.entity.QComment.comment;
import static com.example.BackEnd.count.entity.QCommentLikeCount.commentLikeCount;

@RequiredArgsConstructor
public class CommentRepoCustomImpl implements CommentRepoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CommentListDto> findDepth1Comments(Long postId, Pageable pageable) {

        JPAQuery<CommentListDto> query = queryFactory
                .select(Projections.constructor(
                        CommentListDto.class,
                        comment.id,
                        new CaseBuilder()
                                .when(comment.writer.deleted.isTrue())
                                .then("탈퇴한 사용자입니다.")
                                .otherwise(comment.writer.nickname),
                        comment.writer.profileImageUrl,
                        comment.content,
                        comment.createdAt,
                        commentLikeCount.likeCounts.coalesce(0L),
                        comment.depth
                ))
                .from(comment)
                .leftJoin(comment.writer, member)
                .leftJoin(commentLikeCount).on(comment.id.eq(commentLikeCount.commentId))
                .where(comment.post.id.eq(postId)
                        .and(comment.depth.eq(1)))
                .orderBy(comment.createdAt.desc());

        List<CommentListDto> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count Query 최적화
        LongSupplier countSupplier = getDepth1CommentCountSupplier(postId, pageable);

        return PageableExecutionUtils.getPage(content, pageable, countSupplier);
    }

    @Override
    public Page<CommentListDto> findDepth2Comments(Long parentCommentId, Pageable pageable) {

        JPAQuery<CommentListDto> query = queryFactory
                .select(Projections.constructor(
                        CommentListDto.class,
                        comment.id,
                        new CaseBuilder()
                                .when(comment.writer.deleted.isTrue())
                                .then("탈퇴한 사용자입니다.")
                                .otherwise(comment.writer.nickname),
                        comment.writer.profileImageUrl,
                        comment.content,
                        comment.createdAt,
                        commentLikeCount.likeCounts.coalesce(0L),
                        comment.depth
                ))
                .from(comment)
                .leftJoin(comment.writer, member)
                .leftJoin(commentLikeCount).on(comment.id.eq(commentLikeCount.commentId))
                .where(comment.parent.id.eq(parentCommentId)
                        .and(comment.depth.eq(2)))
                .orderBy(comment.createdAt.desc());

        List<CommentListDto> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // Count Query 최적화
        LongSupplier countSupplier = getDepth2CommentCountSupplier(parentCommentId, pageable);

        return PageableExecutionUtils.getPage(content, pageable, countSupplier);
    }

    private LongSupplier getDepth1CommentCountSupplier(Long postId, Pageable pageable) {

        int n = pageable.getPageNumber() + 1;
        int m = pageable.getPageSize();
        int limitCount = (((n - 1) / 5) + 1) * m * 5 + 1;

        return () -> Optional.ofNullable(
                queryFactory
                        .select(comment.count())
                        .from(comment)
                        .where(comment.post.id.eq(postId)
                                .and(comment.depth.eq(1)))
                        .limit(limitCount)
                        .fetchOne()).orElse(0L);

    }

    private LongSupplier getDepth2CommentCountSupplier(Long parentCommentId, Pageable pageable) {

        int n = pageable.getPageNumber() + 1;
        int m = pageable.getPageSize();
        int limitCount = (((n - 1) / 5) + 1) * m * 5 + 1;

        return () -> Optional.ofNullable(
                queryFactory
                        .select(comment.count())
                        .from(comment)
                        .where(comment.parent.id.eq(parentCommentId)
                                .and(comment.depth.eq(2)))
                        .limit(limitCount)
                        .fetchOne()).orElse(0L);

    }

}
