package com.example.BackEnd.count.repository;

import com.example.BackEnd.count.entity.CommentCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentCountRepository extends JpaRepository<CommentCount, Long> {

}
