package com.dash.leap.domain.mission.service;

import com.dash.leap.domain.mission.dto.request.MissionAreaSettingRequest;
import com.dash.leap.domain.mission.dto.request.MissionRecordRequest;
import com.dash.leap.domain.mission.dto.response.*;
import com.dash.leap.domain.mission.entity.Mission;
import com.dash.leap.domain.mission.entity.MissionRecord;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.mission.exception.InvalidRecordRequestException;
import com.dash.leap.domain.mission.repository.MissionRecordRepository;
import com.dash.leap.domain.mission.repository.MissionRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.mission.exception.InvalidMissionAreaChangeException;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final MissionRecordRepository missionRecordRepository;

    @Transactional
    public MissionAreaSettingResponse chooseMissionArea(User user, MissionAreaSettingRequest request) {

        User findUser = findUserByIdInUserRepository(user);
        log.info("[UserService] 자립영역 설정 시작: 사용자 ID = {}", findUser.getId());

        if (findUser.getMissionType() == null) {
            log.info("[UserService] 온보딩 진행: 초기 자립영역을 설정합니다.");
            findUser.setMissionType(request.missionType());
        } else {
            log.info("[UserService] 새로운 자립영역을 설정합니다.");

            if (missionRecordRepository.countByUserAndStatus(findUser, MissionStatus.ONGOING) != 0) {
                throw new InvalidMissionAreaChangeException("현재 선택한 영역의 미션을 모두 완료해야 다른 영역을 선택할 수 있습니다.");
            }

            log.info("[UserService] 완료했던 영역을 확인합니다.");
            List<MissionRecord> completedMissions = missionRecordRepository.findAllByUserAndStatus(findUser, MissionStatus.COMPLETED);
            Set<MissionType> completedAreas = completedMissions.stream()
                    .map(m -> m.getMission().getMissionType())
                    .collect(Collectors.toSet());

            if (completedAreas.contains(request.missionType())) {
                throw new InvalidMissionAreaChangeException("이미 완료한 영역은 다시 선택할 수 없습니다.");
            }

            log.info("[UserService] 자립영역을 변경을 승인합니다: before = {}", findUser.getMissionType());
            findUser.setMissionType(request.missionType());
            log.info("[UserService] 자립영역 변경에 성공했습니다: after = {}", findUser.getMissionType());

        }

        List<Mission> missions = missionRepository.findByMissionType(request.missionType());

        for (Mission mission : missions) {
            MissionRecord userMission = MissionRecord.builder()
                    .user(findUser)
                    .mission(mission)
                    .assignedTime(LocalDateTime.now())
                    .status(MissionStatus.ONGOING)
                    .build();
            missionRecordRepository.save(userMission);
        }

        return MissionAreaSettingResponse.from(findUser);
    }

    public MissionAreaResponse getMissionDashboard(User user) {

        MissionType selected = user.getMissionType();
        long total = missionRecordRepository.countAllByUserAndMissionType(user.getId(), selected);
        long completed = missionRecordRepository.countCompletedByUserAndMissionType(user.getId(), selected);

        /**
         * total = 0 -> 진행률 0
         * 집계 O -> 백분율로 계산
         */
        int progress = total == 0 ? 0 : (int) Math.round((double) completed / total * 100);
        return new MissionAreaResponse(selected, progress);
    }

    public UserMissionListResponse getUserMissionList(User user, MissionStatus status, int pageNum, int pageSize) {

        if (status == MissionStatus.ONGOING) { // 진행 중인 미션 조회
            List<MissionRecord> missionList = missionRecordRepository.findAllByUserAndStatus(user, MissionStatus.ONGOING);

            List<UserMissionResponse> responseList = missionList.stream()
                    .map(UserMissionResponse::from)
                    .toList();

            return new UserMissionListResponse(responseList, false);
        } else { // 완료한 미션 조회
            Pageable pageable = PageRequest.of(pageNum, pageSize);
            Slice<MissionRecord> slice = missionRecordRepository.findAllByUserAndStatusOrderByCompletedTimeDesc(user, status, pageable);

            List<UserMissionResponse> responseList = slice.getContent().stream()
                    .map(UserMissionResponse::from)
                    .toList();

            return new UserMissionListResponse(responseList, slice.hasNext());
        }
    }

    @Transactional
    public MissionRecordResponse writeMissionRecord(User user, Long recordId, MissionRecordRequest request) {

        MissionRecord userMission = getUserMissionOrElseThrow(recordId);

        verifyMissionOwner(user, userMission);

        if (userMission.getStatus() == MissionStatus.COMPLETED) {
            throw new InvalidRecordRequestException("이미 완료된 미션은 수행일지를 작성할 수 없습니다.");
        }

        userMission.writeRecord(request.content(), request.emotion());

        return MissionRecordResponse.from(userMission);
    }

    public MissionRecordResponse readMissionRecord(User user, Long recordId) {

        MissionRecord userMission = getUserMissionOrElseThrow(recordId);

        verifyMissionOwner(user, userMission);

        if (userMission.getStatus() == MissionStatus.ONGOING) {
            throw new InvalidRecordRequestException("진행 중인 미션입니다.(수행일지 작성 전)");
        }

        return MissionRecordResponse.from(userMission);
    }

    private User findUserByIdInUserRepository(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다: 사용자 ID = " + user.getId()));
    }

    private MissionRecord getUserMissionOrElseThrow(Long recordId) {
        return missionRecordRepository.findById(recordId)
                .orElseThrow(() -> new NotFoundException("해당 미션을 찾을 수 없습니다."));
    }

    private void verifyMissionOwner(User user, MissionRecord userMission) {
        if (!userMission.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("해당 미션에 접근할 권한이 없습니다.");
        }
    }
}
