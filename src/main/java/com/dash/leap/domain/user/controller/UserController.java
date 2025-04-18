package com.dash.leap.domain.user.controller;

import com.dash.leap.domain.user.controller.docs.UserControllerDocs;
import com.dash.leap.domain.user.dto.request.ChatbotSettingRequest;
import com.dash.leap.domain.user.dto.request.LoginRequest;
import com.dash.leap.domain.user.dto.request.MissionAreaSettingRequest;
import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
import com.dash.leap.domain.user.dto.response.ChatbotSettingResponse;
import com.dash.leap.domain.user.dto.response.LoginResponse;
import com.dash.leap.domain.user.dto.response.MissionAreaSettingResponse;
import com.dash.leap.domain.user.dto.response.UserRegisterResponse;
import com.dash.leap.domain.user.service.UserService;
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
            @AuthenticationPrincipal Long userId
    ) {
        ChatbotSettingResponse chatbotSettingResponse = userService.leapySetting(userId, request);
        return ResponseEntity.ok(chatbotSettingResponse);
    }

    @PatchMapping("/mission-area")
    public ResponseEntity<MissionAreaSettingResponse> missionAreaSetting(
            @Valid @RequestBody MissionAreaSettingRequest request,
            @AuthenticationPrincipal Long userId
    ) {
        MissionAreaSettingResponse settingResponse = userService.missionSetting(userId, request);
        return ResponseEntity.ok(settingResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal Long userId) {
        userService.logout(userId);
        return ResponseEntity.status(NO_CONTENT).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> withdraw(@AuthenticationPrincipal Long userId) {
        userService.withdraw(userId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
