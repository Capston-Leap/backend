package com.dash.leap.config.auth.jwt.service;

import com.dash.leap.config.auth.jwt.exception.DuplicateLoginIdException;
import com.dash.leap.domain.user.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;

    public boolean validateLoginId(@NotBlank String loginId) {
        if (userRepository.existsByLoginId(loginId)) {
            throw new DuplicateLoginIdException(loginId);
        }
        return false;
    }
}
