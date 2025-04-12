package com.dash.leap.domain.information.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "자립지원정보 목록에 해당하는 응답입니다.")
public record InformationListResponse(

        @Schema(description = "자립지원정보 리스트")
        List<InformationResponse> responseList,

        @Schema(description = "현재 페이지 번호", example = "0")
        int pageNumber,

        @Schema(description = "페이지 사이즈", example = "10")
        int pageSize,

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        boolean hasNext) {
}
