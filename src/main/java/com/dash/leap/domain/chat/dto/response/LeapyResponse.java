package com.dash.leap.domain.chat.dto.response;

import com.dash.leap.domain.chat.entity.Message;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "리피 응답입니다.")
public record LeapyResponse(

        @Schema(example = "좋은 하루 보냈다니 다행이다!")
        String reply,

        @Schema(example = "좋은 하루 보냈다니 다행이다!")
        LocalDateTime repliedTime
) {

    public static LeapyResponse from(Message message) {
        return new LeapyResponse(message.getContent(), message.getSendTime());
    }
}
