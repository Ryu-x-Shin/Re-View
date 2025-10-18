package com.example.BackEnd.Member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsernameTaskRequest {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{6,12}$",
            message = "아이디 형식을 다시 확인해 주시기 바랍니다.")
    private String username;

}
