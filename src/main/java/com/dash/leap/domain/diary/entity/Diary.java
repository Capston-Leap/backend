package com.dash.leap.domain.diary.entity;

import com.dash.leap.global.domain.BaseEntity;
import com.dash.leap.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String daily;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String memory;

    @OneToOne(mappedBy = "diary", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private DiaryAnalysis diaryAnalysis;
}
