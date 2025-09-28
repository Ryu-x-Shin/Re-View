package com.example.BackEnd.post.hot_post.repository;

import com.example.BackEnd.post.hot_post.entity.DailyHotPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DailyHotPostRepository extends JpaRepository<DailyHotPost, Long> {
    List<DailyHotPost> findTop10ByPickedDateOrderByScoreDesc(LocalDateTime pickedDate);

}
