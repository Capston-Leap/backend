package com.dash.leap.domain.community.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커뮤니티 댓글 생성 응답입니다.")
public record CommentCreateResponse(

        @Schema(description = "댓글 ID", example = "5")
        Long commentId,

        @Schema(description = "댓글 내용", example = "일주일에 스터디 몇 번 진행하나요?")
        String content,

        @Schema(description = "메시지", example = "댓글이 성공적으로 등록되었습니다.")
        String message
) {}