package com.example.BackEnd.work.repository;

import com.example.BackEnd.work.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {

}
