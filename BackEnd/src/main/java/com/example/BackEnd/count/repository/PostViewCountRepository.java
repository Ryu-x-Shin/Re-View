package com.example.BackEnd.count.repository;

import com.example.BackEnd.count.entity.PostViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface PostViewCountRepository extends JpaRepository<PostViewCount, Long> {

    @Query("SELECT pvc.viewCounts FROM PostViewCount pvc WHERE pvc.postId = :postId")
    Optional<Long> findViewCountByPostId(@Param("postId") Long postId);

}
