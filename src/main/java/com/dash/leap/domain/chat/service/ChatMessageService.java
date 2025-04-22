package com.dash.leap.domain.chat.service;

import com.dash.leap.domain.chat.dto.request.LeapyRequest;
import com.dash.leap.domain.chat.dto.response.LeapyResponse;
import com.dash.leap.domain.chat.entity.Chat;
import com.dash.leap.domain.chat.entity.Message;
import com.dash.leap.domain.chat.repository.ChatRepository;
import com.dash.leap.domain.chat.repository.MessageRepository;
import com.dash.leap.domain.chat.service.prompt.SystemPromptFactory;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.exception.NotFoundException;
import com.dash.leap.global.openai.client.OpenAIClient;
import com.dash.leap.global.openai.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public LeapyResponse sendMessage(Long userId, LeapyRequest request) {

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

        log.info("과거 대화 내용을 가져옵니다: chat.getId() = {}", chat.getId());
        List<Message> pastMessages = messageRepository.findByMessagesByChatId(chat.getId());

        int MAX = 15; // 최대 대화 기억 개수
        if (pastMessages.size() > MAX) {
            pastMessages = pastMessages.subList(pastMessages.size() - MAX, pastMessages.size());
        }

        List<MessageDto> messageDtoList = pastMessages.stream()
                .map(m -> MessageDto.builder()
                        .role(m.getSender().startsWith("리피") ? "assistant" : "user")
                        .content(m.getContent())
                        .build())
                .collect(Collectors.toList());

        messageDtoList.add(0, MessageDto.builder()
                .role("system")
                .content(systemPrompt)
                .build());

        log.info("GPT 응답을 받습니다");
        String reply = openAIClient.getGPTResponse(messageDtoList);
        log.info("응답 받았습니다: reply = {}", reply);

        Message gptMessage = Message.builder()
                .chat(chat)
                .sender("리피" + user.getChatbotType())
                .content(reply)
                .build();

        Message message = messageRepository.save(gptMessage);
        return LeapyResponse.from(message);
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
    }
}
