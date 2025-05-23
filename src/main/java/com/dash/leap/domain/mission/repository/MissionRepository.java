package com.dash.leap.domain.mission.repository;

import com.dash.leap.domain.mission.entity.Mission;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByMissionType(MissionType missionType);

    List<Mission> findByMissionTypeAndIsDeletedFalse(MissionType missionType);
}
