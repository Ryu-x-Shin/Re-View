package com.example.BackEnd.Member.role.repository;

import com.example.BackEnd.Member.role.entity.MemberRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    @EntityGraph(attributePaths = "role")
    List<MemberRole> findAllByMemberId(Long memberId);

}
