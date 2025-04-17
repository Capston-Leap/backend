package com.dash.leap.domain.community.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커뮤니티 게시글 수정 응답입니다.")
public record PostUpdateResponse(

        @Schema(description = "게시글 ID", example = "3")
        Long postId,

        @Schema(description = "수정된 제목", example = "스터디 같이 하실 분 구해요")
        String title,

        @Schema(description = "수정된 내용", example = "가천대 근처에서 오프라인 스터디 구합니다!")
        String content,

        @Schema(description = "메시지", example = "게시글이 성공적으로 수정되었습니다.")
        String message
) {}