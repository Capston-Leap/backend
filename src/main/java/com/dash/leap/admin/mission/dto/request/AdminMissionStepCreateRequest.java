package com.dash.leap.admin.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Schema(description = "관리자용 미션 단계 생성 요청입니다.")
public record AdminMissionStepCreateRequest(

        @Schema(description = "단계", example = "1")
        @Positive
        int stepNum,

        @Schema(description = "단계 설명", example = "오늘 느낀 감정을 단어로 표현해본다.")
        @NotBlank
        String description
) {
}
