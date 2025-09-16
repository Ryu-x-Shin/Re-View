package com.example.BackEnd.Member.repository;

import com.example.BackEnd.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepoCustom {

}
