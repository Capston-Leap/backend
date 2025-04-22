package com.dash.leap.domain.chat.dto.response;

import com.dash.leap.domain.chat.entity.Message;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "메시지 1개에 해당하는 응답입니다.")
public record MessageResponse(

        @Schema(description = "송신자", example = "김리피")
        String sender,

        @Schema(description = "메시지 내용", example = "오늘 좋은 하루였어!")
        String content,

        @Schema(description = "시각", example = "2025-04-08T12:57")
        LocalDateTime timestamp
) {

    public MessageResponse (Message message) {
        this(message.getSender(), message.getContent(), message.getSendTime());
    }
}
