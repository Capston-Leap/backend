package com.dash.leap.domain.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String summary;

    @CreatedDate
    @Column(name = "analyzed_at", updatable = false)
    private LocalDateTime analysisTime;
}
