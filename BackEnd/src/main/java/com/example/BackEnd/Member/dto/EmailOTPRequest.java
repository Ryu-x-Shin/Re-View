package com.example.BackEnd.Member.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import static com.example.BackEnd.common.constants.RegexPatterns.*;

@Getter
@RequiredArgsConstructor
public class EmailOTPRequest {

    @NotBlank(message = "{member.email.notblank}")
    @Pattern(regexp = EMAIL,
            message = "{member.email.pattern}")
    @Size(max = 200, message = "{member.email.size}")
    private final String email;

}
