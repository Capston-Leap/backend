package com.dash.leap.admin.mission.service;

import com.dash.leap.admin.mission.dto.request.AdminMissionCreateUpdateRequest;
import com.dash.leap.admin.mission.dto.response.AdminMissionDetailResponse;
import com.dash.leap.admin.mission.dto.response.AdminMissionListResponse;
import com.dash.leap.admin.mission.dto.response.AdminMissionResponse;
import com.dash.leap.domain.mission.entity.Mission;
import com.dash.leap.domain.mission.entity.MissionStep;
import com.dash.leap.domain.mission.repository.MissionRepository;
import com.dash.leap.domain.mission.repository.MissionStepRepository;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMissionService {

    private final MissionRepository missionRepository;
    private final MissionStepRepository missionStepRepository;

    public AdminMissionListResponse getMissionList(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<AdminMissionResponse> missionPage = missionRepository.findAll(pageRequest)
                .map(AdminMissionResponse::from);

        return AdminMissionListResponse.from(missionPage);
    }

    public AdminMissionDetailResponse getMissionDetail(Long missionId) {

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new NotFoundException("해당 미션을 찾을 수 없습니다."));

        List<MissionStep> steps = missionStepRepository.findByMissionIdOrderByStepAsc(missionId);
        return AdminMissionDetailResponse.from(mission, steps);
    }

    @Transactional
    public AdminMissionDetailResponse createMission(AdminMissionCreateUpdateRequest request) {

        Mission mission = Mission.builder()
                .title(request.title())
                .description(request.description())
                .missionType(request.category())
                .build();
        Mission savedMission = missionRepository.save(mission);

        List<MissionStep> steps = request.steps().stream()
                .map(step -> MissionStep.builder()
                        .mission(savedMission)
                        .step(step.stepNum())
                        .description(step.description())
                        .build())
                .toList();
        List<MissionStep> savedSteps = missionStepRepository.saveAll(steps);

        return AdminMissionDetailResponse.from(savedMission, savedSteps);
    }
}
