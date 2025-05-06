package com.dash.leap.admin.user.controller.docs;

import com.dash.leap.admin.user.dto.request.AdminRegisterRequest;
import com.dash.leap.admin.user.dto.response.AdminRegisterResponse;
import com.dash.leap.global.auth.dto.request.LoginRequest;
import com.dash.leap.global.auth.dto.response.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Admin", description = "Admin Auth API")
public interface AdminAuthControllerDocs {

    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입을 요청합니다.")
    @ApiResponse(description = "회원가입 성공", responseCode = "201")
    ResponseEntity<AdminRegisterResponse> register(@RequestBody AdminRegisterRequest request);

    @Operation(summary = "관리자 로그인", description = "관리자 로그인을 요청합니다. 응답에 Token 반환합니다.")
    @ApiResponse(description = "로그인 성공", responseCode = "200")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);
}
