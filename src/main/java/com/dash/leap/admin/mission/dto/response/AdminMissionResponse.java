package com.dash.leap.admin.mission.dto.response;

import com.dash.leap.domain.mission.entity.Mission;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "관리자용 미션 목록 중 한 개에 해당하는 응답입니다.")
public record AdminMissionResponse(

        @Schema(description = "미션 ID", example = "1")
        Long id,

        @Schema(description = "미션명", example = "스스로 감정 기록하기")
        String title,

        @Schema(description = "미션 카테고리", example = "SELF")
        MissionType category,

        @Schema(description = "미션 등록일자", example = "2025-06-01")
        LocalDateTime createdTime,

        @Schema(description = "미션 수정일자", example = "2025-06-02")
        LocalDateTime updateTime
) {

        public static AdminMissionResponse from(Mission mission) {
                return new AdminMissionResponse(
                        mission.getId(), mission.getTitle(), mission.getMissionType(),
                        mission.getCreatedAt(), mission.getUpdatedAt()
                );
        }
}
