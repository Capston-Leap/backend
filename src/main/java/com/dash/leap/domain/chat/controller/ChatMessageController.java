package com.dash.leap.domain.chat.controller;

import com.dash.leap.domain.chat.controller.docs.ChatMessageControllerDocs;
import com.dash.leap.domain.chat.dto.request.MessageRequest;
import com.dash.leap.domain.chat.dto.response.MessageResponse;
import com.dash.leap.domain.chat.service.ChatMessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatMessageController implements ChatMessageControllerDocs {

    private final ChatMessageService chatService;

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(
            @Valid @RequestBody MessageRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        MessageResponse messageResponse = chatService.sendMessage(userId, request);
        return ResponseEntity.ok(messageResponse);
    }
}
