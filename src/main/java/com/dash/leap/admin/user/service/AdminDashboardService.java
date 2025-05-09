package com.dash.leap.admin.user.service;


import com.dash.leap.admin.user.dto.response.AdminDashboardResponse;
import com.dash.leap.domain.community.repository.PostRepository;
import com.dash.leap.domain.information.repository.InformationRepository;
import com.dash.leap.domain.mission.repository.MissionRepository;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.domain.user.entity.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final MissionRepository missionRepository;
    private final InformationRepository informationRepository;
    private final PostRepository postRepository;

    public AdminDashboardResponse getDashboardData() {
        long userCount = userRepository.countByUserType(UserType.USER);

        return new AdminDashboardResponse(
                userCount,
                missionRepository.count(),
                informationRepository.count(),
                postRepository.count()
        );
    }
}