package com.dash.leap.domain.user.controller.docs;

import com.dash.leap.domain.user.dto.request.ChatbotSettingRequest;
import com.dash.leap.domain.user.dto.request.LoginRequest;
import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
import com.dash.leap.domain.user.dto.response.ChatbotSettingResponse;
import com.dash.leap.domain.user.dto.response.LoginResponse;
import com.dash.leap.domain.user.dto.response.UserRegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

@Tag(name = "User", description = "User API")
public interface UserControllerDocs {

    @Operation(summary = "회원가입", description = "회원가입을 요청합니다.")
    @ApiResponse(description = "회원가입 성공", responseCode = "201")
    ResponseEntity<UserRegisterResponse> register(
            @Parameter(required = true) @Valid UserRegisterRequest request
    );

    @Operation(summary = "로그인", description = "로그인을 요청합니다. 응답에 Token 반환합니다.")
    @ApiResponse(description = "로그인 성공", responseCode = "200")
    ResponseEntity<LoginResponse> login(
            @Valid LoginRequest request
    );

    @Operation(summary = "리피 설정", description = "리피 설정을 요청합니다.")
    @ApiResponse(description = "설정 성공", responseCode = "200")
    ResponseEntity<ChatbotSettingResponse> chatbotSetting(
            @Valid ChatbotSettingRequest request,
            Long userId
    );

    @Operation(summary = "로그아웃", description = "로그아웃을 요청합니다. Redis 사용 안 하므로 JWT는 클라이언트에서 삭제 부탁드립니다.")
    @ApiResponse(description = "로그아웃 성공", responseCode = "200")
    ResponseEntity<Void> logout(Long userId);
}
