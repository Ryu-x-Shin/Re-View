package com.example.BackEnd.security.auth.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class AuthTokens {

    private final String accessToken;
    private final String refreshToken;

    public static AuthTokens of(String accessToken, String refreshToken) {
        return new AuthTokens(accessToken, refreshToken);
    }

}
