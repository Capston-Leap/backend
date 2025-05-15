package com.dash.leap.global.auth.jwt.service;

import com.dash.leap.global.auth.dto.response.IdDuplicateResponse;
import com.dash.leap.global.auth.jwt.exception.DuplicateLoginIdException;
import com.dash.leap.domain.user.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    public IdDuplicateResponse validateLoginId(@NotBlank String loginId) {
        log.info("[AuthService] validateLoginId() 실행: loginId = {}", loginId);

        if (userRepository.existsByLoginId(loginId)) {
            throw new DuplicateLoginIdException("이미 존재하는 아이디입니다.");
        }

        return new IdDuplicateResponse(loginId, "사용 가능한 아이디입니다.");
    }
}
