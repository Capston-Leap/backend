package com.dash.leap.admin.information.controller;

import com.dash.leap.admin.information.dto.request.InformationCreateRequest;
import com.dash.leap.admin.information.dto.request.InformationUpdateRequest;
import com.dash.leap.admin.information.dto.response.*;
import com.dash.leap.admin.information.controller.docs.AdminInformationControllerDocs;
import com.dash.leap.admin.information.service.AdminInformationService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/information")
@Validated
public class AdminInformationController implements AdminInformationControllerDocs {

    private final AdminInformationService adminInformationService;

    // 쟈립지원정보 목록 조회
    @GetMapping
    public ResponseEntity<Page<InformationListResponse>> getInformationList(
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 0) int pageNum,
            @RequestParam(name = "size", defaultValue = "10") @Positive int pageSize
    ) {
        return ResponseEntity.ok(adminInformationService.getInformationList(pageNum - 1, pageSize));
    }

    // 쟈립지원정보 상세 조회
    @GetMapping("/{informationId}")
    public ResponseEntity<InformationDetailResponse> getInformationDetail(
            @PathVariable(name = "informationId") Long informationId
    ) {
        return ResponseEntity.ok(adminInformationService.getInformationDetail(informationId));
    }

    // 쟈립지원정보 생성
    @PostMapping
    public ResponseEntity<InformationCreateResponse> createInformation(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody InformationCreateRequest request
    ) {
        return ResponseEntity.ok(adminInformationService.create(userDetails, request));
    }

    // 쟈립지원정보 수정
    @PatchMapping("/{informationId}")
    public ResponseEntity<InformationUpdateResponse> updateInformation(
            @PathVariable(name = "informationId") Long informationId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody InformationUpdateRequest request
    ) {
        return ResponseEntity.ok(adminInformationService.update(informationId, userDetails, request));
    }

    // 쟈립지원정보 삭제
    @DeleteMapping("/{informationId}")
    public ResponseEntity<InformationDeleteResponse> deleteInformation(
            @PathVariable(name = "informationId") Long informationId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        InformationDeleteResponse response = adminInformationService.delete(informationId, userDetails);
        return ResponseEntity.ok(response);
    }
}
