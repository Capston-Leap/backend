package com.dash.leap.admin.information.dto.response;

import com.dash.leap.domain.information.entity.enums.InfoType;
import io.swagger.v3.oas.annotations.media.Schema;

public record InformationCreateResponse(
        @Schema(description = "자립지원정보 ID", example = "3")
        Long id,

        @Schema(description = "자립지원정보 카테고리", example = "ECONOMY")
        InfoType category,

        @Schema(description = "제목", example = "청년 내일저축계좌")
        String title,

        @Schema(description = "본문 내용", example = "매월 10만 원 저축 시 정부가 최대 30만 원까지 추가 적립해주는 청년 지원 통장")
        String content,

        @Schema(description = "관련 링크 URL", example = "https://www.bokjiro.go.kr/ssis-teu/index.do")
        String url,

        @Schema(description = "메시지", example = "자립지원정보가 성공적으로 등록되었습니다.")
        String message
) {}