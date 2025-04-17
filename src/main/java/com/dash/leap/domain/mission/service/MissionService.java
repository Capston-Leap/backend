package com.dash.leap.domain.mission.service;

import com.dash.leap.domain.mission.dto.response.MissionAreaResponse;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.mission.repository.MissionRecordRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    public final UserRepository userRepository;
    public final MissionRecordRepository missionRecordRepository;

    public MissionAreaResponse getMissionDashboard(Long userId) {
        User user = getUserOrElseThrow(userId);

        MissionType selected = user.getMissionType();
        long total = missionRecordRepository.countAllByUserAndMissionType(userId, selected);
        long completed = missionRecordRepository.countCompletedByUserAndMissionType(userId, selected);

        /**
         * total = 0 -> 진행률 0
         * 집계 O -> 백분율로 계산
         */
        int progress = total == 0 ? 0 : (int) Math.round((double) completed / total * 100);
        return new MissionAreaResponse(selected, progress);
    }

    private User getUserOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
    }
}
