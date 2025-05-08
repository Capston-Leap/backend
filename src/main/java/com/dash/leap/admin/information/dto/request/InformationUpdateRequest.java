package com.dash.leap.admin.information.dto.request;

import com.dash.leap.domain.information.entity.enums.InfoType;
import io.swagger.v3.oas.annotations.media.Schema;

public record InformationUpdateRequest(
        @Schema(description = "자립지원정보 카테고리", example = "ECONOMY")
        InfoType category,

        @Schema(description = "제목", example = "청년 내일저축계좌")
        String title,

        @Schema(description = "본문 내용", example = "매월 일정 금액 저축 시 정부 지원으로 자산 형성을 돕는 상품")
        String content,

        @Schema(description = "관련 링크 URL", example = "https://www.bokjiro.go.kr/ssis-teu/index.do")
        String url
) {}