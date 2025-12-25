package com.example.BackEnd.Member.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import static com.example.BackEnd.common.constants.RegexPatterns.*;

@Getter
@RequiredArgsConstructor
public class VerifyOTPRequest {

    @NotBlank(message = "{member.email.notblank}")
    @Pattern(regexp = EMAIL,
            message = "{member.email.pattern}")
    private final String email;

    @NotBlank(message = "{auth.OTP.notblank}")
    private final String OTP;

}
