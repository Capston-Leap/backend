package com.dash.leap.admin.user.service;

import com.dash.leap.admin.user.dto.request.AdminRegisterRequest;
import com.dash.leap.admin.user.dto.response.AdminRegisterResponse;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.UserType;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.auth.jwt.exception.DuplicateLoginIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AdminRegisterResponse register(AdminRegisterRequest request) {

        if (userRepository.existsByLoginId(request.loginId())) {
            throw new DuplicateLoginIdException("이미 사용 중인 로그인 ID입니다.");
        }

        User admin = User.builder()
                .loginId(request.loginId())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .nickname("admin")
                .birth(request.birth())
                .userType(UserType.ADMIN)
                .level(0)
                .build();

        User savedAdmin = userRepository.save(admin);

        return AdminRegisterResponse.from(savedAdmin);
    }
}
