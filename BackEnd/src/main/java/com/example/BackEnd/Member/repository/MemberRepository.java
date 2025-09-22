package com.example.BackEnd.Member.repository;

import com.example.BackEnd.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepoCustom {

    Optional<Member> findByUsernameAndDeletedFalse(String username);

}
