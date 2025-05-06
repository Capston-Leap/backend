package com.dash.leap.admin.user.controller;

import com.dash.leap.admin.user.controller.docs.AdminAuthControllerDocs;
import com.dash.leap.admin.user.dto.request.AdminRegisterRequest;
import com.dash.leap.admin.user.dto.response.AdminRegisterResponse;
import com.dash.leap.admin.user.service.AdminAuthService;
import com.dash.leap.global.auth.dto.request.LoginRequest;
import com.dash.leap.global.auth.dto.response.LoginResponse;
import com.dash.leap.global.auth.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAuthController implements AdminAuthControllerDocs {

    private final AdminAuthService adminAuthService;

    @PostMapping("/register")
    public ResponseEntity<AdminRegisterResponse> register(@RequestBody @Valid AdminRegisterRequest request) {
        AdminRegisterResponse response = adminAuthService.register(request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        LoginResponse response = adminAuthService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        adminAuthService.logout(userDetails.user());
        return ResponseEntity.noContent().build();
    }
}
