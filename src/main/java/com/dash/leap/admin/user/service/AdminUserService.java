package com.dash.leap.admin.user.service;

import com.dash.leap.admin.user.dto.response.AdminUserListResponse;
import com.dash.leap.admin.user.dto.response.AdminUserResponse;
import com.dash.leap.domain.user.repository.UserRepository;
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

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "registerTime"));
        Page<AdminUserResponse> userPage = userRepository.findAll(pageRequest)
                .map(AdminUserResponse::from);

        return AdminUserListResponse.from(userPage);
    }
}
