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
        log.info("[AdminMissionService] getMissionList() 실행: 미션 목록을 조회합니다: page = {}, size = {}", page, size);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<AdminMissionResponse> missionPage = missionRepository.findAll(pageRequest)
                .map(AdminMissionResponse::from);

        return AdminMissionListResponse.from(missionPage);
    }

    public AdminMissionDetailResponse getMissionDetail(Long missionId) {
        log.info("[AdminMissionService] getMissionDetail() 실행: 미션을 상세 조회합니다: missionId = {}", missionId);

        Mission mission = getMissionOrElseThrow(missionId);

        List<MissionStep> steps = missionStepRepository.findByMissionIdOrderByStepAsc(missionId);
        return AdminMissionDetailResponse.from(mission, steps);
    }

    @Transactional
    public AdminMissionDetailResponse createMission(AdminMissionCreateUpdateRequest request) {
        log.info("[AdminMissionService] createMission() 실행: 새로운 미션을 생성합니다.");

        Mission mission = Mission.builder()
                .title(request.title())
                .description(request.description())
                .missionType(request.category())
                .build();
        Mission savedMission = missionRepository.save(mission);

        return buildMissionStepAndSaveAll(request, savedMission);
    }

    @Transactional
    public AdminMissionDetailResponse updateMission(Long missionId, AdminMissionCreateUpdateRequest request) {
        log.info("[AdminMissionService] updateMission() 실행: 미션을 수정합니다: missionId = {}", missionId);

        Mission mission = getMissionOrElseThrow(missionId);

        // 기존 단계들 삭제 후 다시 등록하는 방식
        missionStepRepository.deleteByMission(mission);

        mission.updateMission(request.title(), request.description(), request.category());

        return buildMissionStepAndSaveAll(request, mission);
    }

    @Transactional
    public void deleteMission(Long missionId) {
        log.info("[AdminMissionService] deleteMission() 실행: 미션을 삭제합니다: missionId = {}", missionId);
        Mission mission = getMissionOrElseThrow(missionId);
        mission.deleteMission();
    }

    /**
     * 메소드
     */
    private Mission getMissionOrElseThrow(Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(() -> new NotFoundException("해당 미션을 찾을 수 없습니다."));
    }

    private AdminMissionDetailResponse buildMissionStepAndSaveAll(AdminMissionCreateUpdateRequest request, Mission mission) {
        List<MissionStep> steps = request.steps().stream()
                .map(step -> MissionStep.builder()
                        .mission(mission)
                        .step(step.stepNum())
                        .description(step.description())
                        .build())
                .toList();
        List<MissionStep> savedSteps = missionStepRepository.saveAll(steps);

        return AdminMissionDetailResponse.from(mission, savedSteps);
    }
}
