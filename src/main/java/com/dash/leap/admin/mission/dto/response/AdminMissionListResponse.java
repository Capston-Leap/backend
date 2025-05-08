package com.dash.leap.admin.mission.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "관리자용 미션 목록에 해당하는 응답입니다.")
public record AdminMissionListResponse(

        @Schema(description = "미션 목록 리스트")
        List<AdminMissionResponse> missionList,

        @Schema(description = "현재 페이지 번호", example = "0")
        int pageNumber,

        @Schema(description = "페이지 사이즈", example = "10")
        int pageSize,

        @Schema(description = "전체 페이지 수", example = "3")
        int totalPages
) {

    public static AdminMissionListResponse from(Page<AdminMissionResponse> missionPage) {
        return new AdminMissionListResponse(missionPage.getContent(), missionPage.getNumber(), missionPage.getSize(), missionPage.getTotalPages());
    }
}
