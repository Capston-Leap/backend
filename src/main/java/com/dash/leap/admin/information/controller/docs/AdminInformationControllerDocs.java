package com.dash.leap.admin.information.controller.docs;

import com.dash.leap.admin.information.dto.request.InformationCreateRequest;
import com.dash.leap.admin.information.dto.request.InformationUpdateRequest;
import com.dash.leap.admin.information.dto.response.*;
import com.dash.leap.global.auth.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "[Admin] Information", description = "Admin Information API")
public interface AdminInformationControllerDocs {

    // 자립지원정보 목록 조회
    @Operation(summary = "자립지원정보 목록 조회", description = "등록된 자립지원정보의 카테고리 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공")
    ResponseEntity<Page<InformationListResponse>> getInformationList(
            @RequestParam(name = "page", defaultValue = "1") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize
    );

    // 자립지원정보 상세 조회
    @Operation(summary = "자립지원정보 상세 조회", description = "특정 자립지원정보 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "자립지원정보 상세 조회 성공")
    ResponseEntity<InformationDetailResponse> getInformationDetail(
            @PathVariable(name = "informationId") Long informationId
    );

    // 자립지원정보 생성
    @Operation(summary = "자립지원정보 생성", description = "새로운 자립지원정보를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "자립지원정보 생성 성공")
    ResponseEntity<InformationCreateResponse> createInformation(
            CustomUserDetails userDetails,
            @RequestBody InformationCreateRequest request
    );

    // 자립지원정보 수정
    @Operation(summary = "자립지원정보 수정", description = "기존 자립지원정보를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "자립지원정보 수정 성공")
    ResponseEntity <InformationUpdateResponse> updateInformation(
            @PathVariable(name = "informationId") Long informationId,
            CustomUserDetails userDetails,
            @RequestBody InformationUpdateRequest request
    );

    // 자립지원정보 삭제
    @Operation(summary = "자립지원정보 삭제", description = "특정 자립지원정보를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "자립지원정보 삭제 성공")
    ResponseEntity<InformationDeleteResponse> deleteInformation(
            @PathVariable(name = "informationId") Long informationId,
            CustomUserDetails userDetails
    );
}

