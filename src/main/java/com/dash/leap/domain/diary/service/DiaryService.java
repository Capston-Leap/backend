package com.dash.leap.domain.diary.service;

import com.dash.leap.domain.diary.dto.request.DiaryCreateRequest;
import com.dash.leap.domain.diary.dto.response.DiaryCreateResponse;
import com.dash.leap.domain.diary.entity.Diary;
import com.dash.leap.domain.diary.repository.DiaryRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.global.auth.user.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;

    // 감정 일기 생성
    public DiaryCreateResponse createDiary(CustomUserDetails userDetails, DiaryCreateRequest request) {
        User user = userDetails.user();

        Diary diary = Diary.builder()
                .user(user)
                .daily(request.daily())
                .memory(request.memory())
                .build();

        Diary savedDiary = diaryRepository.save(diary);

        return new DiaryCreateResponse(
                savedDiary.getId(),
                savedDiary.getDaily(),
                savedDiary.getMemory(),
                "감정일기가 성공적으로 등록되었습니다."
        );
    }
}