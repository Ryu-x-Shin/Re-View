package com.example.BackEnd.count.repository;

import com.example.BackEnd.count.entity.PostLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeCountRepository extends JpaRepository<PostLikeCount, Long> {

}
