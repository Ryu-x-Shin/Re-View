package com.example.BackEnd.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class PostEventRedisService {

    private final StringRedisTemplate redisTemplate;

    public boolean recordView(String viewerKey, Long postId) {
        String key = "view:lock:" + postId + ":" + viewerKey;
        Boolean ok = redisTemplate.opsForValue().setIfAbsent(key, "1", Duration.ofMinutes(5));
        return Boolean.TRUE.equals(ok);
    }

    public void increaseViewCounter(Long postId) {
        redisTemplate.opsForValue().increment("post:views:" + postId);
    }

}
