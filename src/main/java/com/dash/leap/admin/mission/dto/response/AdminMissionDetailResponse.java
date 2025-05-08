package com.dash.leap.admin.mission.dto.response;

import com.dash.leap.domain.mission.dto.response.MissionStepResponse;
import com.dash.leap.domain.mission.entity.Mission;
import com.dash.leap.domain.mission.entity.MissionStep;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "관리자용 미션 상세 조회에 해당하는 응답입니다.")
public record AdminMissionDetailResponse(

        @Schema(description = "미션 카테고리", example = "SELF")
        MissionType category,

        @Schema(description = "미션명", example = "스스로 감정 기록하기")
        String title,

        @Schema(description = "미션 설명", example = "매일의 감정을 기록하여 자신의 정서 변화를 알아보는 활동입니다.")
        String description,

        @Schema(description = "미션 수행 단계")
        List<MissionStepResponse> steps,

        @Schema(description = "미션 등록일자", example = "2025-06-01")
        LocalDateTime createdTime,

        @Schema(description = "미션 수정일자", example = "2025-06-02")
        LocalDateTime updateTime,

        @Schema(description = "삭제 여부(삭제하지 않은 미션은 빈칸)", example = "삭제")
        String isDeleted
) {

    public static AdminMissionDetailResponse from(Mission mission, List<MissionStep> steps) {
        return new AdminMissionDetailResponse(
                mission.getMissionType(), mission.getTitle(), mission.getDescription(),
                steps.stream().map(MissionStepResponse::from).toList(),
                mission.getCreatedAt(), mission.getUpdatedAt(),
                mission.isDeleted() ? "삭제" : ""
        );
    }
}
