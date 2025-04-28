package com.dash.leap.domain.diary.controller;

import com.dash.leap.domain.diary.controller.docs.DiaryControllerDocs;
import com.dash.leap.domain.diary.dto.request.DiaryCreateRequest;
import com.dash.leap.domain.diary.dto.response.DiaryCreateResponse;
import com.dash.leap.domain.diary.service.DiaryService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController implements DiaryControllerDocs {

    private final DiaryService diaryService;

    // 감정 일기 생성
    @PostMapping
    public ResponseEntity<DiaryCreateResponse> createDiary(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody DiaryCreateRequest request
    ) {
        return ResponseEntity.ok(diaryService.createDiary(userDetails, request));
    }
}
