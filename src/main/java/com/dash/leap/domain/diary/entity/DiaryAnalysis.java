package com.dash.leap.domain.diary.entity;

import com.dash.leap.global.aimodel.converter.EmotionScoresConverter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public class DiaryAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "emotion_id", nullable = false)
    private Emotion emotion;

    @Convert(converter = EmotionScoresConverter.class)
    @Column(name = "emotion_scores", columnDefinition = "TEXT")
    private Map<String, Double> emotionScore;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String summary;

    @CreatedDate
    @Column(name = "analyzed_at", updatable = false)
    private LocalDateTime analysisTime;
}
