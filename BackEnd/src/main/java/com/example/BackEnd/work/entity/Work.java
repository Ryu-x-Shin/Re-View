package com.example.BackEnd.work.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "works")
public class Work {

    @Id
    @GeneratedValue
    private Long id;

    private String title;


}
