package com.example.BackEnd.Member.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import static com.example.BackEnd.common.constants.RegexPatterns.*;

@Getter
@RequiredArgsConstructor
public class SignupRequest {

    @NotBlank(message = "{member.username.notblank}")
    @Pattern(regexp = USERNAME,
            message = "{member.username.pattern}")
    private final String username;

    @NotBlank(message = "{member.password.notblank}")
    @Pattern(regexp = PASSWORD,
            message = "{member.password.pattern}")
    private final String password;

    @NotBlank(message = "{member.email.notblank}")
    @Pattern(regexp = EMAIL,
            message = "{member.email.pattern}")
    @Size(max = 200, message = "{member.email.size}")
    private final String email;

    @NotBlank(message = "{member.nickname.notblank}")
    @Pattern(regexp = NICKNAME,
            message = "{member.nickname.pattern}")
    private final String nickname;

}
