package com.dash.leap.admin.information.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record InformationDeleteResponse(
        @Schema(description = "자립지원정보 ID", example = "3")
        Long id,

        @Schema(description = "메시지", example = "자립지원정보가 성공적으로 삭제되었습니다.")
        String message
) {}