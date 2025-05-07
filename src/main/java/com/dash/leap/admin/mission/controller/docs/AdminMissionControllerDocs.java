package com.dash.leap.admin.mission.controller.docs;

import com.dash.leap.admin.mission.dto.response.AdminMissionListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Admin Mission", description = "관리자 미션 관리 API")
public interface AdminMissionControllerDocs {

    @Operation(summary = "관리자 미션 목록 조회", description = "관리자가 미션 목록 조회를 요청합니다.")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    ResponseEntity<AdminMissionListResponse> readAllMission(
            @Parameter(description = "조회할 페이지 번호(0부터 시작)", example = "0")
            @RequestParam(name = "page", defaultValue = "0") int pageNum,

            @Parameter(description = "페이지 당 항목 수", example = "10")
            @RequestParam(name = "size", defaultValue = "10") int pageSize
    );
}
