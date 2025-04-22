package com.dash.leap.domain.chat.controller.docs;

import com.dash.leap.domain.chat.dto.request.LeapyRequest;
import com.dash.leap.domain.chat.dto.response.ChatResponse;
import com.dash.leap.domain.chat.dto.response.LeapyResponse;
import com.dash.leap.global.auth.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Chat", description = "Chat API")
public interface ChatMessageControllerDocs {

    @Operation(summary = "메시지 전송", description = "메시지 전송을 요청합니다.")
    @ApiResponse(description = "전송 성공", responseCode = "201")
    ResponseEntity<LeapyResponse> sendMessage(
            @Valid LeapyRequest request,
            CustomUserDetails userDetails
    );

    @Operation(summary = "채팅 조회", description = "채팅 내역 조회를 요청합니다.")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    ResponseEntity<ChatResponse> readChatMessage(
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize,
            CustomUserDetails userDetails
    );
}
