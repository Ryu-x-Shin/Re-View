package com.example.BackEnd.count.repository;

import com.example.BackEnd.count.entity.PostLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface PostLikeCountRepository extends JpaRepository<PostLikeCount, Long> {

    @Query("SELECT plc.likeCounts FROM PostLikeCount plc WHERE plc.postId = :postId")
    Optional<Long> findLikeCountByPostId(@Param("postId") Long postId);

}
