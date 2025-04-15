package com.dash.leap.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답입니다.")
public record LoginResponse(
        @Schema(example = "{tokenString}")
        String token
) {
}
