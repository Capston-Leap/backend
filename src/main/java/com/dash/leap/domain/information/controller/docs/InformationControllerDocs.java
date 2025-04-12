package com.dash.leap.domain.information.controller.docs;

import com.dash.leap.domain.information.dto.response.InformationListResponse;
import com.dash.leap.domain.information.entity.enums.InfoType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Information", description = "Information API")
public interface InformationControllerDocs {

    @Operation(summary = "자립지원정보 조회", description = "자립지원정보 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(description = "자립지원정보 목록 조회 성공", responseCode = "200"),
            @ApiResponse(description = "자립지원정보 목록 조회 실패(잘못된 파라미터 값 입력)", responseCode = "400")
    })
    ResponseEntity<InformationListResponse> readAllInformation(
            @Parameter(description = "자립지원정보 카테고리(미입력 시 전체 조회)")
            @RequestParam(name = "category", required = false) InfoType infoType,
            @RequestParam(name = "page", defaultValue = "0") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize
    );
}
