package com.dash.leap.domain.chat.service;

import com.dash.leap.domain.chat.dto.request.LeapyRequest;
import com.dash.leap.domain.chat.dto.response.ChatResponse;
import com.dash.leap.domain.chat.dto.response.LeapyResponse;
import com.dash.leap.domain.chat.dto.response.MessageResponse;
import com.dash.leap.domain.chat.entity.Chat;
import com.dash.leap.domain.chat.entity.Message;
import com.dash.leap.domain.chat.repository.ChatRepository;
import com.dash.leap.domain.chat.repository.MessageRepository;
import com.dash.leap.domain.chat.service.prompt.SystemPromptFactory;
import com.dash.leap.domain.diary.entity.DiaryAnalysis;
import com.dash.leap.domain.diary.repository.DiaryAnalysisRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import com.dash.leap.global.exception.NotFoundException;
import com.dash.leap.global.openai.client.OpenAIClient;
import com.dash.leap.global.openai.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final OpenAIClient openAIClient;
    private final UserRepository userRepository;
    private final DiaryAnalysisRepository diaryAnalysisRepository;

    @Transactional
    public LeapyResponse sendMessage(User user, LeapyRequest request) {
        log.info("[ChatMessageService] sendMessage() 실행: 메시지를 전송합니다: userId = {}", user.getId());

        User findUser = findUserByIdOrElseThrow(user);
        Chat chat = getChatOrElseThrow(findUser.getId());

        Message userMessage = Message.builder()
                .chat(chat)
                .sender(findUser.getName())
                .content(request.content())
                .build();
        messageRepository.save(userMessage);

        // 최근 감정 분석 조회
        LocalDateTime startOfYesterday = LocalDate.now().minusDays(1).atStartOfDay();
        LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);
        List<DiaryAnalysis> recentAnal = diaryAnalysisRepository
                .findByDiary_UserAndAnalysisTimeBetween(user, startOfYesterday, endOfToday);

        // 지피티 응답
        String systemPrompt = SystemPromptFactory.getSystemPrompt(findUser.getChatbotType(), recentAnal);
        log.info("[ChatMessageService] systemPrompt = {}", systemPrompt);

        log.info("[ChatMessageService] 과거 대화 내용을 가져옵니다: chat.getId() = {}", chat.getId());
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

        log.info("[ChatMessageService] GPT 응답을 받습니다");
        String reply = openAIClient.getGPTResponse(messageDtoList);
        log.info("[ChatMessageService] 응답 받았습니다: reply = {}", reply);

        Message gptMessage = Message.builder()
                .chat(chat)
                .sender("리피" + findUser.getChatbotType())
                .content(reply)
                .build();
        Message message = messageRepository.save(gptMessage);

        return LeapyResponse.from(message);
    }

    @Transactional
    public ChatResponse getMessageList(User user, int pageNum, int pageSize) {
        log.info("[ChatMessageService] getMessageList() 실행: 대화 내역을 조회합니다: userId = {}, pageNum = {}, pageSize = {}", user.getId(), pageNum, pageSize);

        Chat chat = getChatOrElseThrow(user.getId());

        if (!chat.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("해당 채팅에 접근할 권한이 없습니다.");
        }

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Slice<Message> slice = chatRepository.findByChatIdAndLessThanMessageId(chat.getId(), pageable);

        List<MessageResponse> responseList = slice.getContent().stream()
                .map(MessageResponse::new)
                .toList();

        return new ChatResponse(chat.getId(), responseList, slice.hasNext());
    }

    /**
     * 메소드
     */
    private User findUserByIdOrElseThrow(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다: 사용자 ID = " + user.getId()));
    }

    private Chat getChatOrElseThrow(Long userId) {
        return chatRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("채팅방을 찾을 수 없습니다"));
    }
}
