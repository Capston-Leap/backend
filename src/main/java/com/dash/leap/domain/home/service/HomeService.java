package com.dash.leap.domain.home.service;

import com.dash.leap.domain.home.dto.response.HomeResponse;
import com.dash.leap.domain.information.dto.response.InformationResponse;
import com.dash.leap.domain.information.repository.InformationRepository;
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
public class HomeService {

    private final UserRepository userRepository;
    private final MissionRecordRepository missionRecordRepository;
    private final InformationRepository informationRepository;

    public HomeResponse getHome(User user) {
        log.info("[HomeService] getHome() 실행: 홈 정보를 요청합니다: userId = {}", user.getId());

        User findUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다: 사용자 ID = " + user.getId()));

        MissionType missionType = findUser.getMissionType();

        long total = missionRecordRepository.countAllByUserAndMissionType(findUser.getId(), missionType);
        long completed = missionRecordRepository.countCompletedByUserAndMissionType(findUser.getId(), missionType);

        int progress = (total == 0) ? 0 : (int) Math.round((double) completed / total * 100);
        int remaining = (int) (total - completed);

        InformationResponse info = informationRepository.findTopByOrderByCreatedAtDesc()
                .map(InformationResponse::new)
                .orElse(null);

        return HomeResponse.from(findUser, progress, remaining, info);
    }
}
