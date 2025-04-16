package com.dash.leap.domain.community.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "커뮤니티 게시글 생성 요청입니다.")
public record PostCreateRequest(

        @Schema(description = "작성자 ID", example = "leapy@gachon.ac.kr")
        String loginId,

        @Schema(description = "게시글 제목", example = "스터디 모집합니다.")
        String title,

        @Schema(description = "게시글 내용", example = "토익 같이 공부하실 분!")
        String content
) {}