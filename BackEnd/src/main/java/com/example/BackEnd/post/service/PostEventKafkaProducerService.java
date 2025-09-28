package com.example.BackEnd.post.service;

import com.example.BackEnd.post.dto.PostCommentEvent;
import com.example.BackEnd.post.dto.PostLikeEvent;
import com.example.BackEnd.post.dto.PostViewEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostEventKafkaProducerService {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void sendView(PostViewEvent postViewEvent) {
        kafkaTemplate.send("post-view-event", postViewEvent);
    }
    public void sendLike(PostLikeEvent postLikeEvent) {
        kafkaTemplate.send("post-like-event", postLikeEvent);
    }
    public void sendComment(PostCommentEvent postCommentEvent) {
        kafkaTemplate.send("post-comment-event", postCommentEvent);
    }
    public void sendDeletePost(Long postId) {
        kafkaTemplate.send("post-delete-event", postId.toString());
    }

}
