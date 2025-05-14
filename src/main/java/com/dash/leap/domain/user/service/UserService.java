package com.dash.leap.domain.user.service;

import com.dash.leap.domain.chat.entity.Chat;
import com.dash.leap.domain.chat.repository.ChatRepository;
import com.dash.leap.domain.community.repository.PostRepository;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.mission.repository.MissionRecordRepository;
import com.dash.leap.domain.user.dto.request.ChatbotSettingRequest;
import com.dash.leap.global.auth.dto.request.LoginRequest;
import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
import com.dash.leap.domain.user.dto.response.ChatbotSettingResponse;
import com.dash.leap.global.auth.dto.response.LoginResponse;
import com.dash.leap.domain.user.dto.response.MyPageResponse;
import com.dash.leap.domain.user.dto.response.UserRegisterResponse;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.UserType;
import com.dash.leap.domain.user.exception.InvalidChatbotException;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.auth.jwt.exception.DuplicateLoginIdException;
import com.dash.leap.global.auth.jwt.exception.PasswordMismatchException;
import com.dash.leap.global.auth.jwt.service.JwtTokenProvider;
import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRepository chatRepository;
    private final MissionRecordRepository missionRecordRepository;
    private final PostRepository postRepository;

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {

        if (checkLoginIdDuplicate(request.loginId())) {
            throw new DuplicateLoginIdException("아이디 중복 검사를 실시해주세요.");
        }

        if (!request.password().equals(request.passwordConfirm())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        User user = User.builder()
                .loginId(request.loginId())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .nickname(request.nickname())
                .birth(request.birth())
                .userType(UserType.USER)
                .level(1)
                .build();
        User savedUser = userRepository.save(user);

        /**
         * 회원가입 시 채팅방도 항상 생기도록 함
         */
        Chat chat = Chat.builder()
                .user(user)
                .build();
        chatRepository.save(chat);

        return UserRegisterResponse.from(savedUser);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new NotFoundException("존재하지 않은 아이디입니다."));

        if (user.isDeleted()) {
            throw new UnauthorizedException("탈퇴한 회원은 로그인할 수 없습니다.");
        }

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user);
        return new LoginResponse(user.getId(), token);
    }

    @Transactional
    public ChatbotSettingResponse leapySetting(User user, ChatbotSettingRequest request) {
        User findUser = findUserByIdOrElseThrow(user);

        log.info("[UserService] 온보딩: 챗봇(리피) 설정을 시작합니다.");
        if (findUser.getChatbotType() != null) {
            throw new InvalidChatbotException("이미 챗봇이 설정된 사용자입니다.");
        }

        findUser.chooseChatbot(request.toChatbotType());
        log.info("[UserService] 설정이 완료되었습니다.");
        return ChatbotSettingResponse.from(findUser);
    }

    public MyPageResponse getMyPage(User user) {

        long ongoingMissionCount = missionRecordRepository.countByUserAndStatus(user, MissionStatus.ONGOING);
        long completedMissionCount = missionRecordRepository.countByUserAndStatus(user, MissionStatus.COMPLETED);
        long postCount = postRepository.countByUser(user);

        return new MyPageResponse(user.getName(), user.getLoginId(), user.getChatbotType(), ongoingMissionCount, completedMissionCount, postCount);
    }

    @Transactional
    public void logout(User user) {
        log.info("[UserService] 로그아웃 요청: userId = {}", user.getId());
        // Redis 이용 시 Blacklist 추가하는 방향으로 수정
    }

    @Transactional
    public void withdraw(User user) {
        log.warn("[UserService] 회원탈퇴 요청: userId = {}", user.getId());
        User findUser = findUserByIdOrElseThrow(user);
        findUser.deleteUser();
        log.info("[UserService] 정상적으로 탈퇴되었습니다.");
    }

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    private User findUserByIdOrElseThrow(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다: 사용자 ID = " + user.getId()));
    }
}
