package com.example.BackEnd.Member.repository;

import com.example.BackEnd.Member.Entity.Member;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import java.util.*;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END FROM members WHERE username = :username",
            nativeQuery = true)
    boolean existsByUsernameIncludingDeleted(@Param("username") String username);
    Optional<Member> findByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);

}
