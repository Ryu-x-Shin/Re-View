package com.example.BackEnd.Member.repository;

import com.example.BackEnd.Member.Entity.Member;

import java.util.Optional;

public interface MemberRepoCustom {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<Member> signUpTotalCheck(String username, String email, String nickname);

}
