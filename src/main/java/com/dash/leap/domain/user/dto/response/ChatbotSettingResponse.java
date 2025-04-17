package com.dash.leap.domain.user.dto.response;

import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.ChatbotType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리피 설정 응답입니다.")
public record ChatbotSettingResponse(

        @Schema(description = "회원 고유 ID", example = "1")
        Long id,

        @Schema(description = "리피 유형", example = "MF")
        ChatbotType chatbotType
) {

    public static ChatbotSettingResponse from(User user) {
        return new ChatbotSettingResponse(user.getId(), user.getChatbotType());
    }
}
