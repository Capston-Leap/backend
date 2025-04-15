package com.dash.leap.domain.community.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커뮤니티 게시글 생성 응답입니다.")
public record PostCreateResponse(

        @Schema(description = "게시글 ID", example = "100")
        Long postId,

        @Schema(description = "제목", example = "스터디 모집합니다.")
        String title,

        @Schema(description = "내용", example = "토익 같이 공부하실 분!")
        String content,

        @Schema(description = "메시지", example = "게시글이 성공적으로 등록되었습니다.")
        String message
) {}