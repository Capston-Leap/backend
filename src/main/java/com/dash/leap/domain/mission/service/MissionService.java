package com.dash.leap.domain.mission.service;

import com.dash.leap.domain.mission.dto.response.MissionAreaResponse;
import com.dash.leap.domain.mission.dto.response.UserMissionListResponse;
import com.dash.leap.domain.mission.dto.response.UserMissionResponse;
import com.dash.leap.domain.mission.entity.MissionRecord;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.mission.repository.MissionRecordRepository;
import com.dash.leap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    public final MissionRecordRepository missionRecordRepository;

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
}
