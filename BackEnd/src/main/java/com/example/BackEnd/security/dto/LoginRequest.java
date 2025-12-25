package com.example.BackEnd.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@RequiredArgsConstructor
@ToString(exclude = "password")
public class LoginRequest {

    @NotBlank(message = "{member.username.notblank}")
    private final String username;

    @NotBlank(message = "{member.password.notblank}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String password;

}
