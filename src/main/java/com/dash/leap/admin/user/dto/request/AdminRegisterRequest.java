package com.dash.leap.admin.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "관리자 회원가입을 위한 요청입니다.")
public record AdminRegisterRequest(

        @Schema(description = "로그인 ID", example = "admin@gachon.ac.kr")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        String loginId,

        @Schema(description = "비밀번호", example = "admin1234!")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        String password,

        @Schema(description = "이름", example = "김관리")
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        String name,

        @Schema(description = "생년월일", example = "1999-01-01")
        @NotNull(message = "생년월일은 필수 입력 값입니다.")
        LocalDate birth
) {
}
