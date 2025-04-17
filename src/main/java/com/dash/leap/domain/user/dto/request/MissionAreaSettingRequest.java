package com.dash.leap.domain.user.dto.request;

import com.dash.leap.domain.mission.entity.enums.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "자립목표영역 설정 요청입니다.")
public record MissionAreaSettingRequest(
        @Schema(description = "자립목표영역 (LIFE: 일상생활기술, SELF: 자기관리기술, MONEY: 돈관리기술, SOCIETY: 사회진출기술)", example = "LIFE")
        @NotNull
        MissionType missionType
) {
}
