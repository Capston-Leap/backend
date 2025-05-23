package com.dash.leap.domain.diary.controller;

import com.dash.leap.domain.diary.controller.docs.DiaryControllerDocs;
import com.dash.leap.domain.diary.dto.request.DiaryCreateRequest;
import com.dash.leap.domain.diary.dto.response.DiaryCalendarResponse;
import com.dash.leap.domain.diary.dto.response.DiaryCreateResponse;
import com.dash.leap.domain.diary.dto.response.DiaryDetailResponse;
import com.dash.leap.domain.diary.service.DiaryService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController implements DiaryControllerDocs {

    private final DiaryService diaryService;

    // 감정일기 월별 캘린더 조회
    @GetMapping
    public ResponseEntity<List<DiaryCalendarResponse>> getMonthlyCalendar(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(diaryService.getMonthlyCalendar(year, month, userDetails));
    }

    // 감정일기 상세 조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryDetailResponse> getDiaryDetail(
            @PathVariable(name = "diaryId") Long diaryId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(diaryService.getDiaryDetail(diaryId, userDetails));
    }

    // 감정일기 생성
    @PostMapping
    public ResponseEntity<DiaryCreateResponse> createDiary(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DiaryCreateRequest request
    ) {
        return ResponseEntity.status(CREATED).body(diaryService.createDiary(userDetails, request));
    }
}
