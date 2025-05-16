package com.dash.leap.domain.mission.dto.response;

import com.dash.leap.domain.mission.entity.enums.MissionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "완료된 자립목표영역 응답입니다.")
public record CompletedMissionAreaResponse(

        @Schema(description = "완료된 자립목표영역 리스트")
        List<MissionType> completedArea
) {
    public static CompletedMissionAreaResponse from(List<MissionType> types) {
        return new CompletedMissionAreaResponse(types);
    }
}
