package com.dash.leap.domain.community.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentDeleteResponse(
        @Schema(description = "댓글 ID", example = "3")
        Long commentId,

        @Schema(description = "메시지", example = "댓글이 성공적으로 삭제되었습니다.")
        String message
) {}