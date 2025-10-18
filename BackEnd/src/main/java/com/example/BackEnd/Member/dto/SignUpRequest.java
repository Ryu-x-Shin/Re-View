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
public class SignUpRequest {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]{6,12}$",
            message = "아이디 형식을 다시 확인해 주시기 바랍니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!#\\$*\\-])[A-Za-z\\d!#\\$*\\-]{8,20}$",
            message = "비밀번호 형식을 다시 확인해 주시기 바랍니다.")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "이메일 형식을 다시 확인해 주시기 바랍니다.")
    private String email;

    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,12}$",
            message = "닉네임 형식을 다시 확인해 주시기 바랍니다.")
    private String nickname;

}
