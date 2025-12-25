package com.example.BackEnd.post.repository;

import com.example.BackEnd.post.dto.PostPageDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;
import java.util.function.LongSupplier;

public interface PostRepoCustom {

    List<PostPageDto> getPosts(BooleanExpression condition, OrderSpecifier<?> order, int offset, int limit);
    LongSupplier getCountSupplier(BooleanExpression condition);

}
