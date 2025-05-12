package com.dash.leap.domain.home.dto.response;

import com.dash.leap.domain.information.dto.response.InformationResponse;
import com.dash.leap.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "홈화면 조회 응답입니다.")
public record HomeResponse(

        @Schema(description = "닉네임", example = "김리피")
        String nickname,

        @Schema(description = "현재 레벨", example = "1")
        int level,

        @Schema(description = "현재 자립영역 미션 진행률", example = "20")
        int progress,

        @Schema(description = "현재 자립영역 남은 미션 개수", example = "4")
        int remainingMissions,

        @Schema(description = "가장 최신 자립지원정보(null일 경우 DB에 지원정보 데이터가 없음)")
        InformationResponse info
) {
        public static HomeResponse from(User user, int progress, int remainingMissions, InformationResponse info) {
                return new HomeResponse(user.getNickname(), user.getLevel(), progress, remainingMissions, info);
        }
}
