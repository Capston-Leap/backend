package com.dash.leap.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "미션 상세 조회에 해당하는 응답입니다.")
public record MissionDetailResponse(

        @Schema(description = "미션 ID(Mission)", example = "1")
        Long missionId,

        @Schema(description = "미션명", example = "스스로 감정 기록하기")
        String title,

        @Schema(description = "미션 설명", example = "매일의 감정을 기록하여 자신의 정서 변화를 알아보는 활동입니다.")
        String description,

        @Schema(description = "미션 수행 단계")
        List<MissionStepResponse> steps
) {
}
