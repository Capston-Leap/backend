package com.dash.leap.domain.user.service;

import com.dash.leap.domain.chat.entity.Chat;
import com.dash.leap.domain.chat.repository.ChatRepository;
import com.dash.leap.domain.mission.entity.Mission;
import com.dash.leap.domain.mission.entity.MissionRecord;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.mission.repository.MissionRecordRepository;
import com.dash.leap.domain.mission.repository.MissionRepository;
import com.dash.leap.domain.user.dto.request.ChatbotSettingRequest;
import com.dash.leap.domain.user.dto.request.LoginRequest;
import com.dash.leap.domain.user.dto.request.MissionAreaSettingRequest;
import com.dash.leap.domain.user.dto.request.UserRegisterRequest;
import com.dash.leap.domain.user.dto.response.ChatbotSettingResponse;
import com.dash.leap.domain.user.dto.response.LoginResponse;
import com.dash.leap.domain.user.dto.response.MissionAreaSettingResponse;
import com.dash.leap.domain.user.dto.response.UserRegisterResponse;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.ChatbotType;
import com.dash.leap.domain.user.entity.enums.UserType;
import com.dash.leap.domain.user.exception.InvalidMissionAreaChangeException;
import com.dash.leap.domain.user.repository.UserRepository;
import com.dash.leap.global.auth.jwt.exception.DuplicateLoginIdException;
import com.dash.leap.global.auth.jwt.exception.PasswordMismatchException;
import com.dash.leap.global.auth.jwt.service.JwtTokenProvider;
import com.dash.leap.global.auth.jwt.exception.UnauthorizedException;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRepository chatRepository;
    private final MissionRepository missionRepository;
    private final MissionRecordRepository missionRecordRepository;

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {

        if (checkLoginIdDuplicate(request.loginId())) {
            throw new DuplicateLoginIdException("아이디 중복 검사를 실시해주세요.");
        }

        if (!request.password().equals(request.passwordConfirm())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        User user = User.builder()
                .loginId(request.loginId())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .nickname(request.nickname())
                .birth(request.birth())
                .chatbotType(ChatbotType.FF) // TODO: 수정 필요
                .userType(UserType.USER)
                .level(1)
                .build();
        User savedUser = userRepository.save(user);

        /**
         * 회원가입 시 채팅방도 항상 생기도록 함
         */
        Chat chat = Chat.builder()
                .user(user)
                .build();
        chatRepository.save(chat);

        return UserRegisterResponse.from(savedUser);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByLoginId(request.loginId())
                .orElseThrow(() -> new NotFoundException("존재하지 않은 아이디입니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new UnauthorizedException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtTokenProvider.createToken(user);
        return new LoginResponse(user.getId(), token);
    }

    @Transactional
    public ChatbotSettingResponse leapySetting(User user, ChatbotSettingRequest request) {
        ChatbotType requestChatbotType = request.toChatbotType();
        user.setChatbotType(requestChatbotType);
        return ChatbotSettingResponse.from(user);
    }

    @Transactional
    public MissionAreaSettingResponse missionSetting(User user, MissionAreaSettingRequest request) {

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

    @Transactional
    public void logout(User user) {
        log.info("로그아웃 요청: userId = {}", user.getId());
        // Redis 이용 시 Blacklist 추가하는 방향으로 수정
    }

    @Transactional
    public void withdraw(User user) {
        log.warn("회원탈퇴 요청: userId = {}", user.getId());
        userRepository.delete(user);
    }

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    private User findUserByIdInUserRepository(User user) {
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("사용자를 찾을 수 없습니다: 사용자 ID = " + user.getId()));
    }
}
