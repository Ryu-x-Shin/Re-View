package com.example.BackEnd.post.repository;

import com.example.BackEnd.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostIdAndMemberId(Long postId, Long memberId);
    Optional<PostLike> findByPostIdAndMemberId(Long postId, Long memberId);

}
