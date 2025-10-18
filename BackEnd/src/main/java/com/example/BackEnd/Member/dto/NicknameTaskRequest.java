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
public class NicknameTaskRequest {

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,12}$",
            message = "닉네임 형식을 다시 확인해 주시기 바랍니다.")
    private String nickname;

}
