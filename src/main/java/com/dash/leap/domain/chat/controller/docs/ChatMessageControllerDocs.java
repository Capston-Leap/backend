package com.dash.leap.domain.chat.controller.docs;

import com.dash.leap.domain.chat.dto.request.MessageRequest;
import com.dash.leap.domain.chat.dto.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "Chat", description = "Chat API")
public interface ChatMessageControllerDocs {

    @Operation(summary = "메시지 전송", description = "메시지 전송을 요청합니다.")
    @ApiResponse(description = "전송 성공", responseCode = "201")
    ResponseEntity<MessageResponse> sendMessage(
            @Valid MessageRequest request,
            Long userId
    );
}
