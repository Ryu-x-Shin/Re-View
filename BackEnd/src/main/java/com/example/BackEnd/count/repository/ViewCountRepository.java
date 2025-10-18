package com.example.BackEnd.count.repository;

import com.example.BackEnd.count.entity.PostViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewCountRepository extends JpaRepository<PostViewCount, Long> {

}
