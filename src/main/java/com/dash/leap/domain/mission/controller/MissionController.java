package com.dash.leap.domain.mission.controller;

import com.dash.leap.domain.mission.controller.docs.MissionControllerDocs;
import com.dash.leap.domain.mission.dto.response.MissionAreaResponse;
import com.dash.leap.domain.mission.dto.response.UserMissionListResponse;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.mission.service.MissionService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
public class MissionController implements MissionControllerDocs {

    private final MissionService missionService;

    @GetMapping("/goal")
    public ResponseEntity<MissionAreaResponse> readMissionArea(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MissionAreaResponse response = missionService.getMissionDashboard(userDetails.user());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<UserMissionListResponse> readUserMissionList(
            @RequestParam(name = "status") MissionStatus status,
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            @RequestParam(name = "size", defaultValue = "5") int pageSize,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        UserMissionListResponse response = missionService.getUserMissionList(userDetails.user(), status, pageNum, pageSize);
        return ResponseEntity.ok().body(response);
    }
}
