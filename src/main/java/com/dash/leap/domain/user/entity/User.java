package com.dash.leap.domain.user.entity;

import com.dash.leap.domain.mission.entity.enums.MissionType;
import com.dash.leap.domain.user.entity.enums.ChatbotType;
import com.dash.leap.domain.user.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String loginId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String nickname;

    @NotNull
    private LocalDate birth;

    @CreatedDate
    @Column(name = "registered_at", updatable = false)
    private LocalDateTime registerTime;

    @Setter(AccessLevel.PUBLIC)
    @Column(name = "chatbot")
    @Enumerated(EnumType.STRING)
    private ChatbotType chatbotType;

    private Integer level;

    @Setter(AccessLevel.PUBLIC)
    @Column(name = "mission_area")
    @Enumerated(EnumType.STRING)
    private MissionType missionType;

    @Column(name = "class")
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;
}
