package com.example.BackEnd.common.constants;

public final class RedisConstants {

    // Signup
    public static final String SIGNUP_OTP_PREFIX = "Signup:EmailOTP:";
    public static final String SIGNUP_VERIFIED_PREFIX = "SignUp:Verified:";
    public static final String OTP_VERIFIED = "true";

    // Token
    public static final String REFRESH_TOKEN_PREFIX = "auth:RefreshToken:";
    public static final Integer REFRESH_TOKEN_TTL = 7 * 24 * 60 * 60 * 1000;
    public static final String TOKEN_BLACKLIST_PREFIX = "auth:Blacklist:";

    private RedisConstants() {}

}
