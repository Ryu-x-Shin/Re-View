package com.example.BackEnd.Member.controller;

import com.example.BackEnd.Member.dto.*;
import com.example.BackEnd.Member.service.MemberService;
import com.example.BackEnd.common.Response.ApiResponse;
import com.example.BackEnd.common.enums.success_messages.AuthSuccess;
import com.example.BackEnd.common.enums.success_messages.MemberSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 최종적으로 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequest request) {
        memberService.register(request);
        return ApiResponse.success(MemberSuccess.MEMBER_SIGNUP_SUCCESS);
    }

    // username 중복 확인
    @PostMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestBody @Valid UsernameTaskRequest request) {
        memberService.existsByUsername(request.getUsername());
        return ApiResponse.success(AuthSuccess.USERNAME_CHECK_SUCCESS);
    }

    // nickname 중복 확인
    @PostMapping("/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestBody @Valid NicknameTaskRequest request) {
        memberService.existsByNickname(request.getNickname());
        return ApiResponse.success(AuthSuccess.NICKNAME_CHECK_SUCCESS);
    }

}
