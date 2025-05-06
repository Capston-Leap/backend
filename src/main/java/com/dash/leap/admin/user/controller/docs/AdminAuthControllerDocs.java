package com.dash.leap.admin.user.controller.docs;

import com.dash.leap.admin.user.dto.request.AdminRegisterRequest;
import com.dash.leap.admin.user.dto.response.AdminRegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Admin", description = "Admin Auth API")
public interface AdminAuthControllerDocs {

    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입을 요청합니다.")
    @ApiResponse(description = "회원가입 성공", responseCode = "201")
    ResponseEntity<AdminRegisterResponse> register(
            @Parameter(required = true) AdminRegisterRequest request
    );
}
