package com.dash.leap.global.auth.user;

import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserService {

    private final UserRepository userRepository;

    public CustomUserDetails loadUserById(Long userId) {
        log.info("[CustomUserService] loadUserById() 실행: userId = {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("유효하지 않은 사용자입니다."));

        if (user.isDeleted()) {
            throw new UnauthorizedException("탈퇴한 회원입니다.");
        }

        return new CustomUserDetails(user);
    }

}
