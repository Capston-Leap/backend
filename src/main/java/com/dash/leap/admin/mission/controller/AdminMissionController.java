package com.dash.leap.admin.mission.controller;

import com.dash.leap.admin.mission.controller.docs.AdminMissionControllerDocs;
import com.dash.leap.admin.mission.dto.response.AdminMissionListResponse;
import com.dash.leap.admin.mission.service.AdminMissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
