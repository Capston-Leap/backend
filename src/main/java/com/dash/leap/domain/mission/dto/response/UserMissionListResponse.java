package com.dash.leap.domain.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "사용자의 미션 목록에 해당하는 응답입니다.")
public record UserMissionListResponse(

        @Schema(description = "미션 목록 리스트")
        List<UserMissionResponse> missionList,

        @Schema(description = "다음 페이지 존재 여부", example = "false")
        boolean hasNext
) {
}
