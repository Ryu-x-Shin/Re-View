package com.example.BackEnd.post.service;

import com.example.BackEnd.count.entity.CommentCount;
import com.example.BackEnd.count.entity.PostLikeCount;
import com.example.BackEnd.count.entity.PostViewCount;
import com.example.BackEnd.count.repository.CommentCountRepository;
import com.example.BackEnd.count.repository.ViewCountRepository;
import com.example.BackEnd.post.dto.PageRequestPostDto;
import com.example.BackEnd.post.dto.PostDetailsDto;
import com.example.BackEnd.post.dto.PostListDto;
import com.example.BackEnd.post.entity.Post;
import com.example.BackEnd.count.repository.PostLikeCountRepository;
import com.example.BackEnd.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PostService {

    private final StringRedisTemplate redisTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PostRepository postRepository;
    private final PostLikeCountRepository postLikeCountRepository;
    private final ViewCountRepository viewCountRepository;
    private final CommentCountRepository commentCountRepository;

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<PostListDto> getPostList(PageRequestPostDto pageRequestPostDto) {
        return postRepository.getPostList(pageRequestPostDto);
    }

    @Transactional
    public PostDetailsDto getPostDetails(Long postId, String memberKey) {

        // 조회수 처리 (Redis에서 TTL 5분 설정으로 어뷰징 방지, 5분당 1인 1회)
        String redisKey = "post:view:" + postId + ":" + memberKey;
        Boolean alreadyCounted = redisTemplate.opsForValue().setIfAbsent(redisKey, "1", 5, TimeUnit.MINUTES);
        if (Boolean.TRUE.equals(alreadyCounted)) {
            redisTemplate.opsForZSet().incrementScore("post:view:zset", postId.toString(), 1);
            kafkaTemplate.send("post-view-event", postId.toString());
        }

        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        Long likeCount = Optional.of(postLikeCountRepository.findById(postId)
                .map(PostLikeCount::getLikeCounts).orElse(0L)).orElse(0L);
        Long viewCount = Optional.of(viewCountRepository.findById(postId)
                .map(PostViewCount::getViewCounts).orElse(0L)).orElse(0L);
        Long commentCount = Optional.of(commentCountRepository.findById(postId)
                .map(CommentCount::getCommentCounts).orElse(0L)).orElse(0L);

        String nickName = post.getWriter().isDeleted() ? "탈퇴한 사용자입니다." : post.getWriter().getNickname();

        return PostDetailsDto.builder().build();
    }

}
