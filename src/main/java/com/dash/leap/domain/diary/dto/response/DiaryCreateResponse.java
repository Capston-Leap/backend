package com.dash.leap.domain.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Map;

@Schema(description = "감정일기 생성 응답입니다.")
public record DiaryCreateResponse(

        @Schema(description = "감정일기 ID", example = "3")
        Long diaryId,

        @Schema(description = "오늘은 어떤 하루를 보냈나요?", example = "오늘은 특별한 일정 없이 오롯이 나를 위한 하루를 보냈다. 좋아하는 음악을 들으며 산책하고, 카페에 들러서 느긋하게 커피를 마셨다.")
        String daily,

        @Schema(description = "기억에 남는 일이 있었나요?", example = "산책 도중 우연히 마주친 강아지가 나를 반겨줬던 따뜻한 순간이 가장 기억에 남는다. 짧았지만 기분이 몹시 좋아졌다.")
        String memory,

        @Schema(description = "분석된 감정", example = "기쁨")
        String emotion,

        @Schema(description = "AI 감정 분석 점수", example = """
                                                        {
                                                          "불안": 15.3,
                                                          "분노": 12.9,
                                                          "기쁨": 99.1,
                                                          "슬픔": 23.7,
                                                          ...
                                                        }
                                                        """)
        Map<String, Double> emotionScores,

        @Schema(description = "일기 요약")
        String summary,

        @Schema(description = "메세지", example = "감정일기가 성공적으로 등록되었습니다.")
        String message
) {}
