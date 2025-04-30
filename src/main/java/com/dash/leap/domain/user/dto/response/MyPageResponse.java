package com.dash.leap.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "마이페이지 조회 응답입니다.")
public record MyPageResponse(

        @Schema(description = "회원 이름(실명)", example = "지원")
        String name,

        @Schema(description = "회원 로그인 아이디", example = "leapy@gachon.ac.kr")
        String loginId,

        @Schema(description = "진행 중인 미션", example = "4")
        long ongoingMissionCount,

        @Schema(description = "완료한 미션", example = "12")
        long completedMissionCount,

        @Schema(description = "나의 게시글", example = "10")
        long myPostCount
) {
}
