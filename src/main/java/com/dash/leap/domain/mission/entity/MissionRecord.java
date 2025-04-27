package com.dash.leap.domain.mission.entity;

import com.dash.leap.domain.mission.entity.enums.MissionStatus;
import com.dash.leap.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_mission")
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class MissionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mission_id", nullable = false)
    private Mission mission;

    @Column(name = "assigned_at")
    private LocalDateTime assignedTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_completed")
    private MissionStatus status = MissionStatus.ONGOING;

    @LastModifiedDate
    @Column(name = "completed_at")
    private LocalDateTime completedTime;

    @Column(columnDefinition = "TEXT")
    private String recordContent;

    @Column(columnDefinition = "TEXT")
    private String recordEmotion;
}
