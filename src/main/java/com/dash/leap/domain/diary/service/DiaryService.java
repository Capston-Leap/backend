package com.dash.leap.domain.diary.service;

import com.dash.leap.domain.diary.dto.request.DiaryCreateRequest;
import com.dash.leap.domain.diary.dto.response.DiaryCreateResponse;
import com.dash.leap.domain.diary.dto.response.DiaryDetailResponse;
import com.dash.leap.domain.diary.entity.Diary;
import com.dash.leap.domain.diary.entity.DiaryAnalysis;
import com.dash.leap.domain.diary.entity.Emotion;
import com.dash.leap.domain.diary.repository.DiaryRepository;
import com.dash.leap.domain.diary.repository.DiaryAnalysisRepository;
import com.dash.leap.domain.diary.repository.EmotionRepository;
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
    private final DiaryAnalysisRepository diaryAnalysisRepository;
    private final EmotionRepository emotionRepository;

    // 감정일기 상세 조회
    public DiaryDetailResponse getDiaryDetail(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 감정일기입니다."));

        DiaryAnalysis diaryAnalysis = diaryAnalysisRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일기에 대한 분석 결과가 없습니다."));

        Emotion emotion = emotionRepository.findById(diaryAnalysis.getEmotion().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 감정 정보가 없습니다."));

        return new DiaryDetailResponse(
                diary.getId(),
                diary.getDaily(),
                diary.getMemory(),
                diaryAnalysis.getSummary(),
                emotion.getCategory(),
                emotion.getEmoji()
        );
    }

    // 감정일기 생성
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