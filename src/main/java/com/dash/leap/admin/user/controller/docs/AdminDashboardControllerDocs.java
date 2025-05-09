package com.dash.leap.admin.user.controller.docs;

import com.dash.leap.admin.user.dto.response.AdminDashboardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "[Admin] Dashboard", description = "Admin Dashboard API")
public interface AdminDashboardControllerDocs {

    @Operation(summary = "관리자 대시보드", description = "총 이용자 수, 미션 수, 자립지원정보 수, 게시글 수를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "대시보드 통계 조회 성공")
    @GetMapping
    ResponseEntity<AdminDashboardResponse> getDashboard();
}