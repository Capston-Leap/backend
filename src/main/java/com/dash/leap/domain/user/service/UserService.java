package com.dash.leap.domain.user.service;

import com.dash.leap.domain.user.dto.request.ChatbotSettingRequest;
import com.dash.leap.domain.user.dto.request.LoginRequest;
import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
import com.dash.leap.domain.user.dto.response.ChatbotSettingResponse;
import com.dash.leap.domain.user.dto.response.LoginResponse;
import com.dash.leap.domain.user.dto.response.UserRegisterResponse;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.ChatbotType;
import com.dash.leap.domain.user.entity.enums.UserType;
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
                .chatbotType(ChatbotType.FF) // TODO: 수정 필요
                .userType(UserType.USER)
                .level(1)
                .build();

        User savedUser = userRepository.save(user);
        return UserRegisterResponse.from(savedUser);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new NotFoundException("존재하지 않은 아이디입니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user);
        return new LoginResponse(token);
    }

    @Transactional
    public ChatbotSettingResponse leapySetting(Long userId, ChatbotSettingRequest request) {
        User user = getUserOrElseThrow(userId);

        ChatbotType requestChatbotType = request.toChatbotType();
        user.setChatbotType(requestChatbotType);

        return ChatbotSettingResponse.from(user);
    }

    @Transactional
    public void logout(Long userId) {
        getUserOrElseThrow(userId);
        log.info("로그아웃 요청: userId = {}", userId);
        // Redis 이용 시 Blacklist 추가하는 방향으로 수정
    }

    @Transactional
    public void withdraw(Long userId) {
        User user = getUserOrElseThrow(userId);
        log.warn("회원탈퇴 요청: userId = {}", userId);
        userRepository.delete(user);
    }

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
    }
}
