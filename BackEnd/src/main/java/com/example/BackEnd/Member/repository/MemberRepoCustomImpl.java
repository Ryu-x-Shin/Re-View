package com.example.BackEnd.Member.repository;

import com.example.BackEnd.Member.Entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.example.BackEnd.Member.Entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepoCustomImpl implements MemberRepoCustom {

    private final JPAQueryFactory query;

    @Override
    public boolean existsByUsername(String username) {
        return query
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return query
                .selectFrom(member)
                .where(member.email.eq(email))
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return query
                .selectFrom(member)
                .where(member.nickname.eq(nickname))
                .fetchFirst() != null;
    }

    @Override
    public Optional<Member> signUpTotalCheck(String username, String email, String nickname) {
        return Optional.ofNullable(
                query
                .selectFrom(member)
                .where(
                        member.username.eq(username)
                                .or(member.email.eq(email))
                                .or(member.nickname.eq(nickname))
                )
                .fetchFirst());
    }
}
