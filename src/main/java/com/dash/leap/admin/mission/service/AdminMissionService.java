package com.dash.leap.admin.mission.service;

import com.dash.leap.admin.mission.dto.response.AdminMissionListResponse;
import com.dash.leap.admin.mission.dto.response.AdminMissionResponse;
import com.dash.leap.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMissionService {

    private final MissionRepository missionRepository;

    public AdminMissionListResponse getMissionList(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<AdminMissionResponse> missionPage = missionRepository.findAll(pageRequest)
                .map(AdminMissionResponse::from);

        return AdminMissionListResponse.from(missionPage);
    }

}
