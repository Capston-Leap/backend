package com.dash.leap.domain.chat.controller;

import com.dash.leap.domain.chat.controller.docs.ChatMessageControllerDocs;
import com.dash.leap.domain.chat.dto.request.LeapyRequest;
import com.dash.leap.domain.chat.dto.response.ChatResponse;
import com.dash.leap.domain.chat.dto.response.LeapyResponse;
import com.dash.leap.domain.chat.service.ChatMessageService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageController implements ChatMessageControllerDocs {

    private final ChatMessageService chatService;

    @PostMapping
    public ResponseEntity<LeapyResponse> sendMessage(
            @Valid @RequestBody LeapyRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        LeapyResponse messageResponse = chatService.sendMessage(userDetails.user(), request);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping
    public ResponseEntity<ChatResponse> readChatMessage(
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ChatResponse response = chatService.getMessageList(userDetails.user(), pageNum, pageSize);
        return ResponseEntity.ok().body(response);
    }
}
