package com.dash.leap.domain.mission.repository;

import com.dash.leap.domain.mission.entity.Mission;
import com.dash.leap.domain.mission.entity.MissionStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionStepRepository extends JpaRepository<MissionStep, Long> {

    List<MissionStep> findByMissionIdOrderByStepAsc(Long missionId);

    void deleteByMission(Mission mission);
}
