package com.dash.leap.domain.user.dto.response;

import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.ChatbotType;
import com.dash.leap.domain.user.entity.enums.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "회원정보 조회 응답입니다.")
public record UserResponse(

        @Schema(description = "회원 고유 ID", example = "1")
        Long id,

        @Schema(description = "로그인 ID", example = "leapy@gachon.ac.kr")
        String loginId,

        @Schema(description = "이름", example = "김리피")
        String name,

        @Schema(description = "닉네임", example = "leapy25")
        String nickname,

        @Schema(description = "생년월일", example = "2000-02-05")
        LocalDate birth,

        @Schema(description = "가입일자", example = "2025-05-14")
        LocalDateTime registerTime,

        @Schema(description = "리피 유형", example = "MF")
        ChatbotType chatbotType,

        @Schema(description = "현재 레벨", example = "1")
        Integer level,

        @Schema(description = "선택한 자립목표영역", example = "LIFE")
        MissionType missionType,

        @Schema(description = "회원 종류(사용자: USER, 관리자: ADMIN)", example = "USER")
        UserType userType,

        @Schema(description = "탈퇴 여부(탈퇴하지 않은 회원은 빈칸)", example = "탈퇴")
        String isDeleted
) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(), user.getLoginId(), user.getName(), user.getNickname(),
                user.getBirth(), user.getRegisterTime(), user.getChatbotType(),
                user.getLevel(), user.getMissionType(), user.getUserType(),
                user.isDeleted() ? "탈퇴" : ""
        );
    }
}
