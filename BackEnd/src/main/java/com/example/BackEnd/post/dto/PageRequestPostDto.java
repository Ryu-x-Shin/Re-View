package com.example.BackEnd.post.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestPostDto {

    @Builder.Default
    @Min(1)
    @Max(10000)
    private int page = 1;

    @Builder.Default
    @Min(10)
    @Max(50)
    private int size = 15;

    @Builder.Default
    private String sortBy = "RECENT";

    public int getPageNumber() {
        return page - 1;
    }

    public int getPageSize() {
        return size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public int getOffset() {
        return (page - 1) * size;
    }

}
