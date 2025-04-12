package com.dash.leap.domain.information.dto.response;

import com.dash.leap.domain.information.entity.Information;
import com.dash.leap.domain.information.entity.enums.InfoType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "자립지원정보 상세 정보에 해당하는 응답입니다.")
public record InformationResponse(

        @Schema(description = "정보 ID", example = "1")
        Long infoId,

        @Schema(description = "정보 카테고리", example = "진로")
        InfoType infoType,

        @Schema(description = "정보명", example = "국가근로장학금")
        String infoTitle,

        @Schema(description = "정보 내용", example = "학자금지원 9구간 이하")
        String infoContent,

        @Schema(description = "정보 URL", example = "https://career.com/career")
        String infoUrl) {

    public InformationResponse(Information info) {
        this(info.getId(), info.getInfoType(), info.getTitle(), info.getContent(), info.getUrl());
    }
}