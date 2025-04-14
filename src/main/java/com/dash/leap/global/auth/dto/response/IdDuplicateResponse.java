package com.dash.leap.global.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "아이디 중복 검사 응답입니다.")
public record IdDuplicateResponse(

        @Schema(description = "로그인 ID", example = "leapy@gachon.ac.kr")
        String loginId,

        @Schema(description = "응답 메시지", example = "사용 가능한 아이디입니다.")
        String message
) {
}
