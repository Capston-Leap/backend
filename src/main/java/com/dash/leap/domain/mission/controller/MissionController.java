package com.dash.leap.domain.mission.controller;

import com.dash.leap.domain.mission.controller.docs.MissionControllerDocs;
import com.dash.leap.domain.mission.dto.request.MissionRecordRequest;
import com.dash.leap.domain.mission.dto.response.*;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.mission.service.MissionService;
import com.dash.leap.domain.mission.dto.request.MissionAreaSettingRequest;
import com.dash.leap.global.auth.user.CustomUserDetails;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
@Validated
public class MissionController implements MissionControllerDocs {

    private final MissionService missionService;

    @PatchMapping("/area")
    public ResponseEntity<MissionAreaSettingResponse> missionAreaSetting(
            @Valid @RequestBody MissionAreaSettingRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        MissionAreaSettingResponse settingResponse = missionService.chooseMissionArea(userDetails.user(), request);
        return ResponseEntity.ok(settingResponse);
    }

    @GetMapping("/goal")
    public ResponseEntity<MissionAreaResponse> readMissionArea(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MissionAreaResponse response = missionService.getMissionDashboard(userDetails.user());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<UserMissionListResponse> readUserMissionList(
            @RequestParam(name = "status") MissionStatus status,
            @RequestParam(name = "page", defaultValue = "0") @Min(value = 0) int pageNum,
            @RequestParam(name = "size", defaultValue = "5") @Positive int pageSize,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserMissionListResponse response = missionService.getUserMissionList(userDetails.user(), status, pageNum, pageSize);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{missionId}")
    public ResponseEntity<MissionDetailResponse> readMissionDetail(
            @PathVariable(name = "missionId") Long missionId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        MissionDetailResponse response = missionService.getMissionDetail(userDetails.user(), missionId);
        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/record/{userMissionId}")
    public ResponseEntity<MissionRecordResponse> writeMissionRecord(
            @PathVariable(name = "userMissionId") Long userMissionId,
            @Valid @RequestBody MissionRecordRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        MissionRecordResponse response = missionService.writeMissionRecord(userDetails.user(), userMissionId, request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/record/{userMissionId}")
    public ResponseEntity<MissionRecordResponse> readMissionRecord(
            @PathVariable(name = "userMissionId") Long userMissionId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        MissionRecordResponse response = missionService.readMissionRecord(userDetails.user(), userMissionId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/area/completed")
    public ResponseEntity<CompletedMissionAreaResponse> getCompletedMissionArea(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CompletedMissionAreaResponse response = missionService.getCompletedMissionArea(userDetails.user());
        return ResponseEntity.ok().body(response);
    }
}
