package com.dash.leap.domain.mission.dto.response;

import com.dash.leap.domain.mission.entity.MissionStep;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "미션 단계에 해당하는 응답입니다.")
public record MissionStepResponse(

        @Schema(description = "단계", example = "1")
        int stepNum,

        @Schema(description = "단계 설명", example = "오늘 느낀 감정을 단어로 표현해본다.")
        String description
) {

    public static MissionStepResponse from(MissionStep missionStep) {
        return new MissionStepResponse(missionStep.getStep(), missionStep.getDescription());
    }
}
