package com.dash.leap.global.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "예외 응답")
public record ExceptionResponse(
        @Schema(description = "HTTP 상태 코드", example = "상태 코드") String status,
        @Schema(description = "에러 메시지", example = "에러 메시지") String message
) {}