package com.dash.leap.admin.user.dto.response;

import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.ChatbotType;
import com.dash.leap.domain.user.entity.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "관리자용 사용자 정보 목록 중 한 개에 해당하는 응답입니다.")
public record AdminUserResponse(

        @Schema(description = "회원 고유 ID", example = "1")
        Long id,

        @Schema(description = "회원 로그인 ID", example = "leapy@gachon.ac.kr")
        String loginId,

        @Schema(description = "회원 이름", example = "김리피")
        String name,

        @Schema(description = "회원 닉네임", example = "leapy25")
        String nickname,

        @Schema(description = "회원 생년월일", example = "2000-02-05")
        LocalDate birth,

        @Schema(description = "회원 가입일자", example = "2025-06-01")
        LocalDateTime registerTime,

        @Schema(description = "리피 종류", example = "FF")
        ChatbotType chatbotType,

        @Schema(description = "회원 레벨", example = "1")
        int level,

        @Schema(description = "진행 중인 미션 카테고리", example = "SELF")
        MissionType missionType,

        @Schema(description = "회원 종류", example = "USER")
        UserType userType,

        @Schema(description = "탈퇴 여부(탈퇴하지 않은 회원은 빈칸)", example = "탈퇴")
        String isDeleted
) {

    public static AdminUserResponse from(User user) {
        return new AdminUserResponse(
                user.getId(), user.getLoginId(), user.getName(), user.getNickname(), user.getBirth(), user.getRegisterTime(),
                user.getChatbotType(), user.getLevel(), user.getMissionType(), user.getUserType(),
                user.isDeleted() ? "탈퇴" : ""
        );
    }
}
