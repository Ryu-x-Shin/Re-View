package com.example.BackEnd.Member.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import static com.example.BackEnd.common.constants.RegexPatterns.*;

@Getter
@RequiredArgsConstructor
public class NicknameValidationRequest {

    @NotBlank(message = "{member.nickname.notblank}")
    @Pattern(regexp = NICKNAME,
            message = "{member.nickname.pattern}")
    private final String nickname;

}
