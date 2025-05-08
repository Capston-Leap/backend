package com.dash.leap.domain.mission.entity;

import com.dash.leap.global.domain.BaseEntity;
import com.dash.leap.domain.mission.entity.enums.MissionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private MissionType missionType;

    private boolean isDeleted = false;

    /**
     * 관리자용 미션 연관관계 메서드
     */
    public void updateMission(String title, String description, MissionType missionType) {
        this.title = title;
        this.description = description;
        this.missionType = missionType;
    }

    public void deleteMission() {
        this.isDeleted = true;
    }
}
