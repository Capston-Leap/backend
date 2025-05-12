package com.dash.leap.domain.home.controller.docs;

import com.dash.leap.domain.home.dto.response.HomeResponse;
import com.dash.leap.global.auth.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Home", description = "Home API")
public interface HomeControllerDocs {

    @Operation(summary = "홈 화면 조회", description = "홈 화면 조회를 요청합니다.")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    ResponseEntity<HomeResponse> readHome(CustomUserDetails userDetails);
}
