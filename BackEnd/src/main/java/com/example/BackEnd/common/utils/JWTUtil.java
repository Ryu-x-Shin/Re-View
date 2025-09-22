package com.example.BackEnd.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JWTUtil {

    private final SecretKey key;
    private final StringRedisTemplate redisTemplate;
    public static final String REFRESH_TOKEN_PREFIX = "auth:RefreshToken:";
    public static final String BLACKLIST_PREFIX = "auth:BlackList:";
    public static final Integer accessTokenTTL = 60 * 60 * 1000;
    public static final Integer refreshTokenTTL = 7 * 24 * 60 * 60 * 1000;

    public JWTUtil(@Value("${jwt.secret}") String secret, StringRedisTemplate redisTemplate) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.redisTemplate = redisTemplate;
    }

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .subject(username)
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenTTL))
                .claim("type", "access")
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .add("alg", "HS256")
                .and()
                .subject(username)
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenTTL))
                .claim("type", "refresh")
                .signWith(key)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValidated(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isBlackListed(String token) {
        String jti = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getId();
        return "true".equals(redisTemplate.opsForValue().get(BLACKLIST_PREFIX + jti));
    }
}
