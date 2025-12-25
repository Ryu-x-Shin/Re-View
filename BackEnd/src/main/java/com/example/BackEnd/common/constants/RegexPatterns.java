package com.example.BackEnd.common.constants;

public final class RegexPatterns {

    public static final String USERNAME = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{6,12}$";
    public static final String PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!#\\$*\\-])[A-Za-z\\d!#\\$*\\-]{8,20}$";
    public static final String NICKNAME = "^[가-힣a-zA-Z0-9]{1,12}$";
    public static final String EMAIL = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    private RegexPatterns() {}

}
