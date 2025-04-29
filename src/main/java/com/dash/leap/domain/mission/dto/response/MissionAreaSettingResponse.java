package com.dash.leap.domain.mission.dto.response;

import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "자립목표 설정 응답입니다.")
public record MissionAreaSettingResponse(

        @Schema(description = "회원 고유 ID", example = "1")
        Long id,

        @Schema(description = "선택한 자립목표영역", example = "LIFE")
        MissionType missionType
) {

    public static MissionAreaSettingResponse from(User user) {
        return new MissionAreaSettingResponse(user.getId(), user.getMissionType());
    }
}
