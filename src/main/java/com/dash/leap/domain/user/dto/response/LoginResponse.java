package com.dash.leap.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답입니다.")
public record LoginResponse(

        @Schema(description = "회원 고유 ID", example = "1")
        Long id,

        @Schema(description = "JWT 토큰", example = "{tokenString}")
        String token
) {
}
