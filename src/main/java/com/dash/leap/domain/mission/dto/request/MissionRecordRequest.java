package com.dash.leap.domain.mission.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "미션 수행 일지 작성 요청입니다.")
public record MissionRecordRequest(

        @Schema(description = "어떤 미션을 수행했나요?", example = "3일 동안의 식단을 작성하였다. 아침, 점심, 저녁 메뉴를 미리 계획하고 칼로리와 영양소도 간단히 체크해봤다.")
        String content,

        @Schema(description = "수행 후 어떤 기분이 들었나요?", example = "처음에는 식단 작성을 꾸준히 할 수 있을지 걱정했지만, 미션을 하나씩 완료하면서 계획의 중요성을 스스로 관리하는 뿌듯함을 느꼈다.")
        String emotion
) {
}
