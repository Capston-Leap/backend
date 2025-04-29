package com.dash.leap.domain.diary.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "감정일기 생성 요청입니다.")
public record DiaryCreateRequest(

        @Schema(description = "오늘은 어떤 하루를 보냈나요?", example = "오늘은 특별한 일정 없이 오롯이 나를 위한 하루를 보냈다. 좋아하는 음악을 들으며 산책하고, 카페에 들러서 느긋하게 커피를 마셨다.")
        String daily,

        @Schema(description = "기억에 남는 일이 있었나요?", example = "산책 도중 우연히 마주친 강아지가 나를 반겨줬던 따뜻한 순간이 가장 기억에 남는다. 짧았지만 기분이 몹시 좋아졌다.")
        String memory
) {}