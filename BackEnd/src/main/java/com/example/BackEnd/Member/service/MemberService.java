package com.example.BackEnd.Member.service;

import com.example.BackEnd.Member.repository.MemberRepository;
import com.example.BackEnd.common.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.*;

import static com.example.BackEnd.common.enums.error_codes.MemberErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public void checkUsernameDuplication(String username) {
        if (memberRepository.existsByUsernameIncludingDeleted(username)) {
            throw new BusinessException(USERNAME_ALREADY_EXISTS);
        }
    }

    @Transactional(readOnly = true)
    public void checkNicknameDuplication(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new BusinessException(NICKNAME_ALREADY_EXISTS);
        }
    }

    @Transactional(readOnly = true)
    public void checkEmailDuplication(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }

}
