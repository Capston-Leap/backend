package com.dash.leap.admin.community.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostDeleteResponse(
        @Schema(description = "게시글 ID", example = "5")
        Long postId,

        @Schema(description = "메시지", example = "게시글이 성공적으로 삭제되었습니다.")
        String message
) {}