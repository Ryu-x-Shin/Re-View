package com.example.BackEnd.Member.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import static com.example.BackEnd.common.constants.RegexPatterns.*;

@Getter
@RequiredArgsConstructor
public class UsernameValidationRequest {

    @NotBlank(message = "{member.username.notblank}")
    @Pattern(regexp = USERNAME,
            message = "{member.username.pattern}")
    private final String username;

}
