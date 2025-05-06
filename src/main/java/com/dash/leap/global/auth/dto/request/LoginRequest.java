package com.dash.leap.global.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청입니다.")
public record LoginRequest(

        @Schema(description = "로그인 ID", example = "leapy@gachon.ac.kr")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        String loginId,

        @Schema(description = "비밀번호", example = "password1234!")
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        String password
) {
}