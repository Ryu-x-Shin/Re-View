package com.example.BackEnd.Member.controller;

import com.example.BackEnd.Member.dto.*;
import com.example.BackEnd.Member.service.MemberService;
import com.example.BackEnd.Member.service.SignUpEmailOtpService;
import com.example.BackEnd.common.Response.ApiResponse;
import com.example.BackEnd.common.enums.error_codes.MemberError;
import com.example.BackEnd.common.enums.success_messages.GlobalSuccess;
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
    private final SignUpEmailOtpService signUpEmailOtpService;

    // 인증번호 생성 및 전송
    @PostMapping("/send-otp")
    public ResponseEntity<?> generateAndSendOtp(@RequestBody @Valid EmailTaskRequest request) {
        String email = request.getEmail();
        memberService.existsByEmail(email);
        signUpEmailOtpService.generateAndSendOtp(email);
        return ApiResponse.success(GlobalSuccess.EMAIL_SEND_SUCCESS);
    }

    // 인증번호를 맞게 입력했는지 검증
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody @Valid VerifyOtpRequest request) {
        String email = request.getEmail();
        String otp = request.getOtp();
        boolean verified = signUpEmailOtpService.verifyOtp(email, otp);
        if (verified) {
            return ApiResponse.success(MemberSuccess.EMAIL_VERIFICATION_SUCCESS);
        }
        return ApiResponse.fail(MemberError.EMAIL_VERIFICATION_FAILED);
    }

    // 최종적으로 회원가입
    @PostMapping("/signUp")
    public ResponseEntity<?> signup(@RequestBody @Valid SignUpRequest request) {
        memberService.register(request);
        return ApiResponse.success(MemberSuccess.MEMBER_SIGNUP_SUCCESS);
    }

    // username 중복 확인
    @PostMapping("/checkUsername")
    public ResponseEntity<?> checkUsername(@RequestBody @Valid UsernameTaskRequest request) {
        memberService.existsByUsername(request.getUsername());
        return ApiResponse.success(MemberSuccess.USERNAME_CHECK_SUCCESS);
    }

    // nickname 중복 확인
    @PostMapping("/checkNickname")
    public ResponseEntity<?> checkNickname(@RequestBody @Valid NicknameTaskRequest request) {
        memberService.existsByNickname(request.getNickname());
        return ApiResponse.success(MemberSuccess.NICKNAME_CHECK_SUCCESS);
    }

}
