package com.dash.leap.admin.mission.dto.request;

import com.dash.leap.domain.mission.entity.enums.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "관리자용 미션 생성 요청입니다.")
public record AdminMissionCreateRequest(

        @Schema(description = "미션명", example = "스스로 감정 기록하기")
        @NotBlank
        String title,

        @Schema(description = "미션 설명", example = "매일의 감정을 기록하여 자신의 정서 변화를 알아보는 활동입니다.")
        @NotBlank
        String description,

        @Schema(description = "미션 카테고리", example = "SELF")
        @NotNull
        MissionType category,

        @Schema(description = "미션 수행 단계")
        @NotNull
        List<AdminMissionStepCreateRequest> steps
) {
}
