package com.example.BackEnd.post.repository;

import com.example.BackEnd.post.dto.PostPageDto;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.*;
import java.util.*;
import java.util.function.LongSupplier;
import lombok.*;

import static com.example.BackEnd.Member.Entity.QMember.member;
import static com.example.BackEnd.count.entity.QPostLikeCount.postLikeCount;
import static com.example.BackEnd.count.entity.QPostViewCount.postViewCount;
import static com.example.BackEnd.post.entity.QPost.post;
import static com.example.BackEnd.common.constants.PostConstants.*;

@RequiredArgsConstructor
public class PostRepoCustomImpl implements PostRepoCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostPageDto> getPosts(BooleanExpression condition, OrderSpecifier<?> order, int offset, int limit) {
        return queryFactory.select(Projections.constructor(PostPageDto.class,
                                post.id,
                                post.title,
                                getNickname(),
                                post.createdAt,
                                postViewCount.viewCounts.coalesce(0L),
                                postLikeCount.likeCounts.coalesce(0L),
                                post.isNotice))
                        .from(post)
                        .leftJoin(post.writer, member)
                        .leftJoin(postViewCount).on(postViewCount.postId.eq(post.id))
                        .leftJoin(postLikeCount).on(postLikeCount.postId.eq(post.id))
                        .where(condition)
                        .orderBy(order)
                        .offset(offset)
                        .limit(limit)
                        .fetch();
    }

    private Expression<?> getNickname() {
        return new CaseBuilder()
                .when(member.deleted.isTrue())
                .then(DELETED_USER)
                .otherwise(member.nickname);
    }

    @Override
    public LongSupplier getCountSupplier(BooleanExpression condition) {
        return () -> {
            Long count = queryFactory
                    .select(post.count())
                    .from(post)
                    .where(condition)
                    .fetchOne();
            return count != null ? count : 0L;
        };
    }

}
