package com.dash.leap.admin.user.service;

import com.dash.leap.admin.user.dto.request.AdminRegisterRequest;
import com.dash.leap.admin.user.dto.response.AdminRegisterResponse;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.UserType;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.auth.dto.request.LoginRequest;
import com.dash.leap.global.auth.dto.response.LoginResponse;
import com.dash.leap.global.auth.jwt.exception.DuplicateLoginIdException;
import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import com.dash.leap.global.auth.jwt.service.JwtTokenProvider;
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
public class AdminAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

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

    @Transactional
    public LoginResponse login(LoginRequest request) {

        User admin = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new NotFoundException("존재하지 않은 아이디입니다."));

        if (admin.isDeleted()) {
            throw new UnauthorizedException("탈퇴한 관리자입니다.");
        }

        if (admin.getUserType() != UserType.ADMIN) {
            throw new UnauthorizedException("관리자만 로그인할 수 있습니다.");
        }

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(admin);
        return new LoginResponse(admin.getId(), token);
    }

    @Transactional
    public void logout(User user) {
        log.info("관리자 로그아웃 요청: userId = {}", user.getId());
        // Redis 이용 시 Blacklist 추가하는 방향으로 수정
    }
}
