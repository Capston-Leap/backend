package com.dash.leap.admin.mission.controller;

import com.dash.leap.admin.mission.controller.docs.AdminMissionControllerDocs;
import com.dash.leap.admin.mission.dto.request.AdminMissionCreateUpdateRequest;
import com.dash.leap.admin.mission.dto.response.AdminMissionDetailResponse;
import com.dash.leap.admin.mission.dto.response.AdminMissionListResponse;
import com.dash.leap.admin.mission.service.AdminMissionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/admin/mission")
@RequiredArgsConstructor
@Validated
public class AdminMissionController implements AdminMissionControllerDocs {

    private final AdminMissionService adminMissionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminMissionListResponse> readAllMission(
            @RequestParam(name = "page", defaultValue = "0") @Min(value = 0) int pageNum,
            @RequestParam(name = "size", defaultValue = "10") @Positive int pageSize
    ) {
        AdminMissionListResponse response = adminMissionService.getMissionList(pageNum, pageSize);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{missionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminMissionDetailResponse> readMissionDetail(
            @PathVariable(name = "missionId") Long missionId
    ) {
        AdminMissionDetailResponse response = adminMissionService.getMissionDetail(missionId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminMissionDetailResponse> createMission(
            @Valid @RequestBody AdminMissionCreateUpdateRequest request
    ) {
        AdminMissionDetailResponse response = adminMissionService.createMission(request);
        return ResponseEntity.status(CREATED).body(response);
    }

    @PatchMapping("/{missionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminMissionDetailResponse> updateMission(
            @PathVariable(name = "missionId") Long missionId,
            @Valid @RequestBody AdminMissionCreateUpdateRequest request
    ) {
        AdminMissionDetailResponse response = adminMissionService.updateMission(missionId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{missionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMission(
            @PathVariable(name = "missionId") Long missionId
    ) {
        adminMissionService.deleteMission(missionId);
        return ResponseEntity.status(NO_CONTENT).build();
    }
}
