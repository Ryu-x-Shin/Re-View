package com.example.BackEnd.common.constants;

public final class PostConstants {

    // Paging
    public static final int MIN_PAGE_NUMBER = 1;
    public static final int MAX_PAGE_NUMBER = 10000;
    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final int MIN_PAGE_SIZE = 10;
    public static final int MAX_PAGE_SIZE = 50;
    public static final String DEFAULT_PAGE_SIZE = "15";
    public static final String DEFAULT_PAGE_SORTBY = "RECENT";
    public static final int FIRST_PAGE_OFFSET = 0;
    public static final int FIRST_PAGE_LIMIT = 0;
    public static final String DELETED_USER = "탈퇴한 사용자";

    // Metrics
    public static final Long INITIAL_VIEW_COUNTS = 0L;
    public static final Long INITIAL_LIKE_COUNTS = 0L;

    private PostConstants() {}

}
