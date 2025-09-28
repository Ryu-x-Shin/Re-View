package com.example.BackEnd.post.repository;

import com.example.BackEnd.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepoCustom {

    Optional<Post> findByIdAndDeletedFalse(Long id);

}
