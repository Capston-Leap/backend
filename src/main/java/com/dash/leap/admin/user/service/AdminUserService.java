package com.dash.leap.admin.user.service;

import com.dash.leap.admin.user.dto.response.AdminUserListResponse;
import com.dash.leap.admin.user.dto.response.AdminUserResponse;
import com.dash.leap.admin.user.exception.AlreadyDeletedException;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserListResponse getUserList(int page, int size) {
        log.info("[AdminUserService] getUserList() 실행: 회원 목록을 조회합니다: page = {}, size = {}", page, size);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "registerTime"));
        Page<AdminUserResponse> userPage = userRepository.findAll(pageRequest)
                .map(AdminUserResponse::from);

        return AdminUserListResponse.from(userPage);
    }

    @Transactional
    public void deleteUser(Long userId) {
        log.info("[AdminUserService] deleteUser() 실행: 회원을 탈퇴시킵니다: 탈퇴시킬 userId = {}", userId);

        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));

        if (findUser.isDeleted()) {
            throw new AlreadyDeletedException("이미 탈퇴 처리된 사용자입니다.");
        }

        findUser.deleteUser();
    }
}
