package com.dash.leap.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "아이디 중복 검사 요청입니다.")
public record IdDuplicateRequest(
        @Schema(description = "로그인 ID", example = "leapy@gachon.ac.kr")
        @NotBlank(message = "아이디는 필수 입력 값입니다.")
        @Email(message = "이메일 형식에 맞지 않습니다.")
        String loginId
) {
}
