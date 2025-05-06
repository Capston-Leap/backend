package com.dash.leap.domain.user.controller;

import com.dash.leap.domain.user.controller.docs.UserControllerDocs;
import com.dash.leap.domain.user.dto.request.ChatbotSettingRequest;
import com.dash.leap.global.auth.dto.request.LoginRequest;
import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
import com.dash.leap.domain.user.dto.response.ChatbotSettingResponse;
import com.dash.leap.global.auth.dto.response.LoginResponse;
import com.dash.leap.domain.user.dto.response.MyPageResponse;
import com.dash.leap.domain.user.dto.response.UserRegisterResponse;
import com.dash.leap.domain.user.service.UserService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(
            @Valid @RequestBody UserRegisterRequest request
    ) {
        UserRegisterResponse registerResponse = userService.register(request);
        return ResponseEntity.status(CREATED).body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
    ) {
        LoginResponse loginResponse = userService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

    @PatchMapping("/chatbot")
    public ResponseEntity<ChatbotSettingResponse> chatbotSetting(
            @Valid @RequestBody ChatbotSettingRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        ChatbotSettingResponse chatbotSettingResponse = userService.leapySetting(userDetails.user(), request);
        return ResponseEntity.ok(chatbotSettingResponse);
    }

    @GetMapping("/profile")
    public ResponseEntity<MyPageResponse> readMyPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MyPageResponse response = userService.getMyPage(userDetails.user());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.logout(userDetails.user());
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.withdraw(userDetails.user());
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
