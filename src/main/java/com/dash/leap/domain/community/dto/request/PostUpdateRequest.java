package com.dash.leap.domain.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커뮤니티 게시글 수정 요청입니다.")
public record PostUpdateRequest(

        @Schema(description = "게시글 제목", example = "스터디 같이 하실 분 구해요")
        String title,

        @Schema(description = "게시글 내용", example = "가천대 근처에서 오프라인 스터디 구합니다!")
        String content
) {}