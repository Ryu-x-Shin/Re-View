package com.example.BackEnd.Member.controller;

import com.example.BackEnd.Member.dto.*;
import com.example.BackEnd.Member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.*;
import lombok.*;

import static com.example.BackEnd.common.Response.ApiResponse.*;
import static com.example.BackEnd.common.enums.success_messages.MemberSuccessCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/username/exists")
    public ResponseEntity<?> checkUsernameDuplication(@RequestBody @Valid UsernameValidationRequest request) {
        memberService.checkUsernameDuplication(request.getUsername());
        return success(USERNAME_AVAILABLE);
    }

    @PostMapping("/nickname/exists")
    public ResponseEntity<?> checkNicknameDuplication(@RequestBody @Valid NicknameValidationRequest request) {
        memberService.checkNicknameDuplication(request.getNickname());
        return success(NICKNAME_AVAILABLE);
    }

}
