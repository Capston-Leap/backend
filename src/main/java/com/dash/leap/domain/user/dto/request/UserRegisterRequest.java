package com.dash.leap.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "회원가입을 위한 요청입니다.")
public record UserRegisterRequest(

        @Schema(description = "로그인 ID", example = "leapy@gachon.ac.kr")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        String loginId,

        @Schema(description = "비밀번호", example = "password1234!")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        String password,

        @Schema(description = "비밀번호 확인", example = "password1234!")
        @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
        String passwordConfirm,

        @Schema(description = "이름", example = "김리피")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        String name,

        @Schema(description = "닉네임", example = "leapy25")
        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        String nickname,

        @Schema(description = "생년월일", example = "2000-02-05")
        @NotNull(message = "생년월일은 필수 입력 값입니다.")
        LocalDate birth
) {
}
