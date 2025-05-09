package com.dash.leap.admin.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdminDashboardResponse(
        @Schema(description = "총 이용자 수", example = "102")
        long userCount,

        @Schema(description = "총 미션 수", example = "30")
        long missionCount,

        @Schema(description = "총 자립지원정보 수", example = "42")
        long informationCount,

        @Schema(description = "총 게시글 수", example = "158")
        long postCount
) {}