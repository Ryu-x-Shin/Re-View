package com.example.BackEnd.count.dto;

import lombok.*;

import static com.example.BackEnd.common.constants.PostConstants.*;

@Getter
@Builder
@AllArgsConstructor
public class PostMetricsDto {

    private Long viewCounts;
    private Long likeCounts;

    public static PostMetricsDto empty() {
        return PostMetricsDto.of(INITIAL_VIEW_COUNTS, INITIAL_LIKE_COUNTS);
    }

    public static PostMetricsDto of(Long viewCounts, Long likeCounts) {
        return PostMetricsDto.builder()
                .viewCounts(viewCounts)
                .likeCounts(likeCounts)
                .build();
    }

}
