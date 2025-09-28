package com.example.BackEnd.post.service;

import com.example.BackEnd.post.dto.PostCommentEvent;
import com.example.BackEnd.post.dto.PostLikeEvent;
import com.example.BackEnd.post.dto.PostViewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostEventKafkaConsumerService {

    private final StringRedisTemplate redisTemplate;

    @KafkaListener(topics = "post-view-event", groupId = "basic-group")
    public void onView(PostViewEvent postViewEvent) {
        String viewsKey = "post:views:" + postViewEvent.getPostId();
        redisTemplate.opsForValue().increment(viewsKey, 1);

        String key = "popular:" + LocalDateTime.now();
        redisTemplate.opsForZSet().incrementScore(key, postViewEvent.getPostId().toString(), 1);
        redisTemplate.expire(key, Duration.ofDays(7));
    }

    @KafkaListener(topics = "post-like-event", groupId = "basic-group")
    public void onLike(PostLikeEvent postLikeEvent) {
        String key = "popular:" + LocalDateTime.now();
        redisTemplate.opsForZSet().incrementScore(key, postLikeEvent.getPostId().toString(), 5);
        redisTemplate.expire(key, Duration.ofDays(7));
    }

    @KafkaListener(topics = "post-comment-event", groupId = "basic-group")
    public void onComment(PostCommentEvent postCommentEvent) {
        String key = "popular:" + LocalDateTime.now();
        redisTemplate.opsForZSet().incrementScore(key, postCommentEvent.getPostId().toString(), 3);
        redisTemplate.expire(key, Duration.ofDays(7));
    }

    @KafkaListener(topics = "post-delete-event", groupId = "basic-group")
    public void onDeletePost(String postId) {
        redisTemplate.keys("popular:*").forEach(k -> redisTemplate.opsForZSet().remove(k, postId));
        redisTemplate.delete("post:views:" + postId);
        redisTemplate.delete("post:likes:" + postId);
        redisTemplate.delete("post:comments:" + postId);
    }

}
