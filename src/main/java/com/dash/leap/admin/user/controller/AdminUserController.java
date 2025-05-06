package com.dash.leap.admin.user.controller;

import com.dash.leap.admin.user.controller.docs.AdminUserControllerDocs;
import com.dash.leap.admin.user.dto.response.AdminUserListResponse;
import com.dash.leap.admin.user.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class AdminUserController implements AdminUserControllerDocs {

    private final AdminUserService adminUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserListResponse> readAllUser(int pageNum, int pageSize) {
        AdminUserListResponse response = adminUserService.getUserList(pageNum, pageSize);
        return ResponseEntity.ok(response);
    }
}
