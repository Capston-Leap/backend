package com.dash.leap.domain.mission.controller;

import com.dash.leap.domain.mission.controller.docs.MissionControllerDocs;
import com.dash.leap.domain.mission.dto.response.MissionAreaResponse;
import com.dash.leap.domain.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/mission")
@RequiredArgsConstructor
public class MissionController implements MissionControllerDocs {

    private final MissionService missionService;

    @GetMapping("/goal")
    public ResponseEntity<MissionAreaResponse> readMissionArea(@AuthenticationPrincipal Long userId) {
        MissionAreaResponse response = missionService.getMissionDashboard(userId);
        return ResponseEntity.ok().body(response);
    }
}
