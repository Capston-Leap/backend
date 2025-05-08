package com.dash.leap.admin.mission.controller;

import com.dash.leap.admin.mission.controller.docs.AdminMissionControllerDocs;
import com.dash.leap.admin.mission.dto.request.AdminMissionCreateUpdateRequest;
import com.dash.leap.admin.mission.dto.response.AdminMissionDetailResponse;
import com.dash.leap.admin.mission.dto.response.AdminMissionListResponse;
import com.dash.leap.admin.mission.service.AdminMissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/admin/mission")
@RequiredArgsConstructor
public class AdminMissionController implements AdminMissionControllerDocs {

    private final AdminMissionService adminMissionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminMissionListResponse> readAllMission(int pageNum, int pageSize) {
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
}
