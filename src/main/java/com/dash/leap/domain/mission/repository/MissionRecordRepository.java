package com.dash.leap.domain.mission.repository;

import com.dash.leap.domain.mission.entity.MissionRecord;
import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissionRecordRepository extends JpaRepository<MissionRecord, Long> {

    @Query("select count(mr) from MissionRecord mr " +
            "inner join Mission m " +
                "on mr.mission.id = m.id " +
            "where mr.user.id = :userId " +
                "and m.missionType = :type")
    long countAllByUserAndMissionType(@Param("userId") Long userId, @Param("type") MissionType missionType);

    @Query("select count(mr) from MissionRecord mr " +
            "inner join Mission m " +
                "on mr.mission.id = m.id " +
            "where mr.user.id = :userId " +
                "and m.missionType = :type " +
            "and mr.status = com.dash.leap.domain.mission.entity.enums.MissionStatus.COMPLETED")
    long countCompletedByUserAndMissionType(@Param("userId") Long userId, @Param("type") MissionType missionType);

    List<MissionRecord> findAllByUserAndStatus(User user, MissionStatus missionStatus);

    Slice<MissionRecord> findAllByUserAndStatusOrderByCompletedTimeDesc(User user, MissionStatus missionStatus, Pageable pageable);
}
