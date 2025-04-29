package com.dash.leap.domain.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record DiaryCalendarResponse(
        @Schema(description = "감정일기 ID", example = "3")
        Long diaryId,

        @Schema(description = "작성일자", example = "2025-04-12")
        LocalDate date,

        @Schema(description = "감정 이모지 ID", example = "5")
        Long emotionId,

        @Schema(description = "감정 이모지", example = "/static/emojis/joy.png")
        String emoji
) {}
