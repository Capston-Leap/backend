package com.dash.leap.domain.diary.controller.docs;

import com.dash.leap.domain.diary.dto.request.DiaryCreateRequest;
import com.dash.leap.domain.diary.dto.response.DiaryCreateResponse;
import com.dash.leap.domain.diary.dto.response.DiaryDetailResponse;
import com.dash.leap.global.auth.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Diary", description = "Diary API")
public interface DiaryControllerDocs {

    // 감정일기 상세 조회
    @Operation(summary = "감정 일기 상세 조회", description = "diaryId를 기반으로 감정 일기 상세 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "감정일기 상세 조회 성공")
    ResponseEntity<DiaryDetailResponse> getDiaryDetail(
            @PathVariable(name = "diaryId") Long diaryId
    );

    // 감정일기 생성
    @Operation(summary = "감정일기 작성", description = "감정일기를 등록합니다.")
    @ApiResponse(responseCode = "201", description = "감정일기 작성 성공")
    ResponseEntity<DiaryCreateResponse> createDiary(
            CustomUserDetails userDetails,
            @RequestBody DiaryCreateRequest request
    );
}