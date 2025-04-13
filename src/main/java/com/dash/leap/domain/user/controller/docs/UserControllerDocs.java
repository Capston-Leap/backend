package com.dash.leap.domain.user.controller.docs;

import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
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
            @Parameter(required = true) @Valid UserRegisterRequest request);
}
