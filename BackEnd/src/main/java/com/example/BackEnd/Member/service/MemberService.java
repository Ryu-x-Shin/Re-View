package com.example.BackEnd.Member.service;

import com.example.BackEnd.Member.Entity.Member;
import com.example.BackEnd.Member.dto.SignUpRequest;
import com.example.BackEnd.Member.repository.MemberRepository;
import com.example.BackEnd.common.enums.error_codes.MemberError;
import com.example.BackEnd.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final SignUpEmailOtpService signUpEmailOtpService;
    private final PasswordEncoder passwordEncoder;

    @Value("${aws.s3.basicProfile}")
    private String basicProfileUrl;

    @Transactional
    public void register(SignUpRequest request) {
        Optional<Member> exists = memberRepository.signUpTotalCheck(
                request.getUsername(), request.getEmail(), request.getNickname());

        exists.ifPresent(m -> {
            if (m.getUsername().equals(request.getUsername()))
                throw new BusinessException(MemberError.USERNAME_ALREADY_EXIST);

            if (m.getEmail().equals(request.getEmail()))
                throw new BusinessException(MemberError.EMAIL_ALREADY_EXIST);

            if (m.getNickname().equals(request.getNickname()))
                throw new BusinessException(MemberError.NICKNAME_ALREADY_EXIST);
        });

        if (!signUpEmailOtpService.isVerified(request.getEmail())) {
            throw new BusinessException(MemberError.EMAIL_NOT_VERIFIED);
        }

        Member member = Member.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .nickname(request.getNickname())
                .profileImageUrl(basicProfileUrl)
                .build();

        memberRepository.save(member);
    }

    public void existsByUsername(String username) {
        boolean exists = memberRepository.existsByUsername(username);
        if (exists) {
            throw new BusinessException(MemberError.USERNAME_ALREADY_EXIST);
        }
    }

    public void existsByNickname(String nickname) {
        boolean exists = memberRepository.existsByNickname(nickname);
        if (exists) {
            throw new BusinessException(MemberError.NICKNAME_ALREADY_EXIST);
        }
    }

    public void existsByEmail(String email) {
        boolean exists = memberRepository.existsByEmail(email);
        if (exists) {
            throw new BusinessException(MemberError.EMAIL_ALREADY_EXIST);
        }
    }

}
