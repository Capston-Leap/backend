package com.dash.leap.admin.user.controller;

import com.dash.leap.admin.user.controller.docs.AdminUserControllerDocs;
import com.dash.leap.admin.user.dto.response.AdminUserListResponse;
import com.dash.leap.admin.user.service.AdminUserService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
@Validated
public class AdminUserController implements AdminUserControllerDocs {

    private final AdminUserService adminUserService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminUserListResponse> readAllUser(
            @RequestParam(name = "page", defaultValue = "0") @Min(value = 0) int pageNum,
            @RequestParam(name = "size", defaultValue = "10") @Positive int pageSize
    ) {
        AdminUserListResponse response = adminUserService.getUserList(pageNum, pageSize);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(
            @PathVariable(name = "userId") Long userId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        adminUserService.deleteUser(userId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
