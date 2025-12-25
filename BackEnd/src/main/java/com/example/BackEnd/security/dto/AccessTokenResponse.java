package com.example.BackEnd.security.dto;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class AccessTokenResponse {

    private final String accessToken;

    public static AccessTokenResponse of(String accessToken) {
        return new AccessTokenResponse(accessToken);
    }

}
