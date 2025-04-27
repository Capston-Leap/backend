package com.dash.leap.domain.mission.controller.docs;

import com.dash.leap.domain.mission.dto.response.MissionAreaResponse;
import com.dash.leap.domain.mission.dto.response.UserMissionListResponse;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.global.auth.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Mission", description = "Mission API")
public interface MissionControllerDocs {

    @Operation(summary = "자립목표영역 조회", description = "선택한 자립목표영역 조회를 요청합니다.")
    @ApiResponse(description = "자립목표영역 조회 성공", responseCode = "200")
    ResponseEntity<MissionAreaResponse> readMissionArea(CustomUserDetails userDetails);

    @Operation(summary = "사용자의 미션 목록 조회", description = "status(ONGOINT/COMPLETED)에 따라 진행중/완료된 미션을 조회합니다.")
    @ApiResponse(description = "조회 성공", responseCode = "200")
    ResponseEntity<UserMissionListResponse> readUserMissionList(
            @Parameter(description = "미션 진행 상태")
            @RequestParam(name = "status") MissionStatus status,
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            CustomUserDetails userDetails
    );
}
