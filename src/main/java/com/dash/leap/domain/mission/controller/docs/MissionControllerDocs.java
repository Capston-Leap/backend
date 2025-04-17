package com.dash.leap.domain.mission.controller.docs;

import com.dash.leap.domain.mission.dto.response.MissionAreaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Mission", description = "Mission API")
public interface MissionControllerDocs {

    @Operation(summary = "자립목표영역 조회", description = "선택한 자립목표영역 조회를 요청합니다.")
    @ApiResponse(description = "자립목표영역 조회 성공", responseCode = "200")
    ResponseEntity<MissionAreaResponse> readMissionArea(Long userId);
}
