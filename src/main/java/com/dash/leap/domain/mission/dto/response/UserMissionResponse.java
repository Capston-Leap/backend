package com.dash.leap.domain.mission.dto.response;

import com.dash.leap.domain.mission.entity.MissionRecord;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자의 미션 목록 중 한 개에 해당하는 응답입니다.")
public record UserMissionResponse(

        @Schema(description = "사용자가 진행 중인 미션 ID(MissionRecord)", example = "1")
        Long missionRecordId,

        @Schema(description = "미션 고유 ID(Mission)", example = "1")
        Long missionId,

        @Schema(description = "미션 카테고리", example = "일상생활기술")
        MissionType missionType,

        @Schema(description = "미션 제목", example = "3일 동안의 식단 작성하기")
        String title,

        @Schema(description = "미션 완료 상태")
        MissionStatus status
) {

    public static UserMissionResponse from(MissionRecord missionRecord) {
        return new UserMissionResponse(missionRecord.getId(), missionRecord.getMission().getId(), missionRecord.getMission().getMissionType(), missionRecord.getMission().getTitle(), missionRecord.getStatus());
    }
}
