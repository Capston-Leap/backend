package com.dash.leap.domain.mission.dto.response;

import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "자립목표영역 대시보드 응답입니다.")
public record MissionAreaResponse(

        @Schema(description = "선택한 자립목표영역", example = "LIFE")
        MissionType selectedMissionType,

        @Schema(description = "미션 진행률", example = "60")
        int progress
) {

    public static MissionAreaResponse from(User user, int progress) {
        return new MissionAreaResponse(user.getMissionType(), progress);
    }
}
