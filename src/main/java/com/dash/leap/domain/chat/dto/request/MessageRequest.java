package com.dash.leap.domain.chat.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "메시지 전송 요청입니다.")
public record MessageRequest(

        @Schema(description = "사용자가 보낸 메시지", example = "오늘 기분이 좋았어.")
        @NotBlank
        String content
) {
}
