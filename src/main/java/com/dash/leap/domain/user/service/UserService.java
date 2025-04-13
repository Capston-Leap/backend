package com.dash.leap.domain.user.service;

import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
import com.dash.leap.domain.user.dto.response.UserRegisterResponse;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.ChatbotType;
import com.dash.leap.domain.user.entity.enums.UserType;
import com.dash.leap.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {
        User user = User.builder()
                .loginId(request.loginId())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .nickname(request.nickname())
                .birth(request.birth())
                .chatbotType(ChatbotType.FF) // TODO: 수정 필요
                .userType(UserType.USER)
                .level(1)
                .registerTime(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);
        return UserRegisterResponse.from(savedUser);
    }
}
