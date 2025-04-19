package com.dash.leap.domain.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커뮤니티 댓글 생성 요청입니다.")
public record CommentCreateRequest(

        @Schema(description = "댓글 내용", example = "일주일에 스터디 몇 번 진행하나요?")
        String content
) {}