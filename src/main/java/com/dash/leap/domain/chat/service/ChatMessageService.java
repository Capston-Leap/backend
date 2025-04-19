package com.dash.leap.domain.chat.service;

import com.dash.leap.domain.chat.dto.request.MessageRequest;
import com.dash.leap.domain.chat.dto.response.MessageResponse;
import com.dash.leap.domain.chat.entity.Chat;
import com.dash.leap.domain.chat.entity.Message;
import com.dash.leap.domain.chat.repository.ChatRepository;
import com.dash.leap.domain.chat.repository.MessageRepository;
import com.dash.leap.domain.chat.service.prompt.SystemPromptFactory;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.exception.NotFoundException;
import com.dash.leap.global.openai.client.OpenAIClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final OpenAIClient openAIClient;

    @Transactional
    public MessageResponse sendMessage(Long userId, MessageRequest request) {

        User user = getUserOrElseThrow(userId);

        Chat chat = chatRepository.findByUserId(userId)
                .orElseGet(() -> chatRepository.save(Chat.builder().user(user).build()));

        Message userMessage = Message.builder()
                .chat(chat)
                .sender(user.getName())
                .content(request.content())
                .build();

        messageRepository.save(userMessage);

        // 지피티 응답
        String systemPrompt = SystemPromptFactory.getSystemPrompt(user.getChatbotType());
        String reply = openAIClient.getGPTResponse(systemPrompt, request.content());

        Message gptMessage = Message.builder()
                .chat(chat)
                .sender("리피" + user.getChatbotType())
                .content(reply)
                .build();

        Message message = messageRepository.save(gptMessage);
        return MessageResponse.from(message);
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
    }
}
