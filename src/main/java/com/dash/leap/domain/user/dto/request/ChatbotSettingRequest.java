package com.dash.leap.domain.user.dto.request;

import com.dash.leap.domain.user.entity.enums.ChatbotType;
import com.dash.leap.domain.user.exception.InvalidChatbotTypeException;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "온보딩 시 리피 설정 요청입니다.")
public record ChatbotSettingRequest(

        @Schema(description = "성별 (M: 남성, F: 여성)", example = "M")
        String gender,

        @Schema(description = "성격 (F: 다정하고 공감적인 리피, T: 이성적인 조언가 리피)", example = "F")
        String character
) {

    public ChatbotType toChatbotType() {
        try {
            return ChatbotType.valueOf(gender + character);
        } catch (IllegalArgumentException e) {
            throw new InvalidChatbotTypeException("유효하지 않은 챗봇 유형입니다: " + gender + character);
        }
    }
}
