package com.dash.leap.domain.chat.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "메시지 목록(채팅방)에 해당하는 응답입니다.")
public record ChatResponse(

        @Schema(description = "채팅방 ID", example = "1")
        Long id,

        @Schema(description = "메시지 리스트")
        List<MessageResponse> responseList,

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        boolean hasNext
) {
}
