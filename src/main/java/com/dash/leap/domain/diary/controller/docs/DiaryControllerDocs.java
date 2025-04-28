package com.dash.leap.domain.diary.controller.docs;

import com.dash.leap.domain.diary.dto.request.DiaryCreateRequest;
import com.dash.leap.domain.diary.dto.response.DiaryCreateResponse;
import com.dash.leap.global.auth.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Diary", description = "Diary API")
public interface DiaryControllerDocs {

    // 감정 일기 생성
    @Operation(summary = "감정 일기 작성", description = "사용자가 감정 일기를 작성하여 등록합니다.")
    @ApiResponse(responseCode = "200", description = "감정 일기 작성 성공")
    ResponseEntity<DiaryCreateResponse> createDiary(
            CustomUserDetails userDetails,
            @RequestBody DiaryCreateRequest request
    );
}