package com.dash.leap.domain.diary.service;

import com.dash.leap.domain.diary.dto.request.DiaryCreateRequest;
import com.dash.leap.domain.diary.dto.response.DiaryCalendarResponse;
import com.dash.leap.domain.diary.dto.response.DiaryCreateResponse;
import com.dash.leap.domain.diary.dto.response.DiaryDetailResponse;
import com.dash.leap.domain.diary.entity.Diary;
import com.dash.leap.domain.diary.entity.DiaryAnalysis;
import com.dash.leap.domain.diary.entity.Emotion;
import com.dash.leap.domain.diary.exception.ConflictException;
import com.dash.leap.domain.diary.exception.ForbiddenException;
import com.dash.leap.domain.diary.repository.DiaryRepository;
import com.dash.leap.domain.diary.repository.DiaryAnalysisRepository;
import com.dash.leap.domain.diary.repository.EmotionRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.global.aimodel.exception.EmotionScoreConvertException;
import com.dash.leap.global.aimodel.exception.TextSummaryFailedException;
import com.dash.leap.global.aimodel.service.EmotionAnalysisService;
import com.dash.leap.global.aimodel.service.SummaryService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import com.dash.leap.global.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final DiaryAnalysisRepository diaryAnalysisRepository;
    private final EmotionRepository emotionRepository;
    private final EmotionAnalysisService emotionAnalysisService;
    private final SummaryService summaryService;

    // 감정일기 월별 캘린더 조회
    public List<DiaryCalendarResponse> getMonthlyCalendar(int year, int month, CustomUserDetails userDetails) {
        Long userId = userDetails.user().getId();
        log.info("[DiaryService] getMonthlyCalendar() 실행: 감정일기 월별 캘린더를 조회합니다: userId = {}, year = {}, month = {}", userId, year, month);

        List<Diary> diaries = diaryRepository.findByYearAndMonthAndUserId(year, month, userId);

        return diaries.stream()
                .map(diary -> {
                    DiaryAnalysis analysis = diaryAnalysisRepository.findByDiaryId(diary.getId())
                            .orElseThrow(() -> new NotFoundException("분석 결과가 없습니다."));
                    Emotion emotion = emotionRepository.findById(analysis.getEmotion().getId())
                            .orElseThrow(() -> new NotFoundException("감정 정보가 없습니다."));
                    return new DiaryCalendarResponse(
                            diary.getId(),
                            diary.getCreatedAt().toLocalDate(),
                            emotion.getId(),
                            emotion.getCategory(),
                            emotion.getEmoji()
                    );
                })
                .collect(Collectors.toList());
    }

    // 감정일기 상세 조회
    public DiaryDetailResponse getDiaryDetail(Long diaryId, CustomUserDetails userDetails) {
        Long userId = userDetails.user().getId();
        log.info("[DiaryService] getDiaryDetail() 실행: 감정일기를 상세 조회합니다: userId = {}, diaryId = {}", userId, diaryId);

        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 감정일기입니다."));

        if (!diary.getUser().getId().equals(userId)) {
            throw new ForbiddenException("본인이 작성한 감정일기만 조회할 수 있습니다.");
        }

        DiaryAnalysis diaryAnalysis = diaryAnalysisRepository.findByDiaryId(diaryId)
                .orElseThrow(() -> new NotFoundException("해당 분석 결과가 없습니다."));

        Emotion emotion = emotionRepository.findById(diaryAnalysis.getEmotion().getId())
                .orElseThrow(() -> new NotFoundException("해당 감정 정보가 없습니다."));

        return new DiaryDetailResponse(
                diary.getId(),
                diary.getDaily(),
                diary.getMemory(),
                diaryAnalysis.getSummary(),
                emotion.getId(),
                emotion.getCategory(),
                diaryAnalysis.getEmotionScore(),
                emotion.getEmoji()
        );
    }

    // 감정일기 생성
    public DiaryCreateResponse createDiary(CustomUserDetails userDetails, DiaryCreateRequest request) {
        User user = userDetails.user();
        log.info("[DiaryService] createDiary() 실행: 감정일기를 작성합니다: userId = {}", user.getId());

        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime endOfToday = LocalDate.now().atTime(LocalTime.MAX);

        boolean exists = diaryRepository.existsByUserAndCreatedAtBetween(user, startOfToday, endOfToday);

        if (exists) {
            throw new ConflictException("오늘은 이미 감정일기를 작성하셨습니다.");
        }

        Diary diary = Diary.builder()
                .user(user)
                .daily(request.daily())
                .memory(request.memory())
                .build();

        Diary savedDiary = diaryRepository.save(diary);

        /**
         * AI 분석
         */
        String text = request.daily() + " " + request.memory();

        log.info("[DiaryService] 감정 분석을 시작합니다.");
        // 감정 분석
        String analyzedEmotion = emotionAnalysisService.analyzeEmotion(text);

        log.info("[DiaryService] {}", analyzedEmotion);

        String predictedEmotion = Arrays.stream(analyzedEmotion.split("\n"))
                .filter(line -> line.startsWith("예측된 감정:"))
                .map(line -> line.replace("예측된 감정:", "").trim())
                .findFirst()
                .orElse("AI 감정 분석 실패");

        Emotion emotion = emotionRepository.findByCategory(predictedEmotion)
                .orElseThrow(() -> new NotFoundException("분석된 감정(" + predictedEmotion + ")에 해당하는 Emotion을 찾을 수 없습니다."));

        // 감정 점수 추출
        Map<String, Double> scores = extractEmotionScores(analyzedEmotion);

        // 텍스트 요약
        log.info("[DiaryService] 일기 요약을 시작합니다.");
        String summarizedText = summaryService.summarizeText(text);
        log.info("[DiaryService] {}", summarizedText);

        if (summarizedText.startsWith("AI 일기 요약 실패")) {
            throw new TextSummaryFailedException("AI 일기 요약에 실패했습니다.");
        }
        String summary = extractSummary(summarizedText);

        log.info("[DiaryService] 성공적으로 감정 분석 및 일기 요약이 완료되었습니다.");
        DiaryAnalysis diaryAnalysis = DiaryAnalysis.builder()
                .diary(savedDiary)
                .emotion(emotion)
                .emotionScore(scores)
                .summary(summary)
                .build();
        DiaryAnalysis savedDiaryAnalysis = diaryAnalysisRepository.save(diaryAnalysis);

        return new DiaryCreateResponse(
                savedDiary.getId(),
                savedDiary.getDaily(),
                savedDiary.getMemory(),
                savedDiaryAnalysis.getEmotion().getCategory(),
                scores,
                savedDiaryAnalysis.getSummary(),
                "감정일기가 성공적으로 등록되었습니다."
        );
    }

    /**
     * 메소드
     */
    private Map<String, Double> extractEmotionScores(String analyzedEmotion) {
        return Arrays.stream(analyzedEmotion.split("\n"))
                .dropWhile(line -> !line.startsWith("불안:"))
                .map(line -> line.split(":"))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(
                        parts -> parts[0].trim(),
                        parts -> {
                            String raw = parts[1].trim().replace("%", "");
                            try {
                                return Double.parseDouble(raw);
                            } catch (NumberFormatException e) {
                                throw new EmotionScoreConvertException("감정 점수를 숫자로 파싱하는 데 실패했습니다: " + raw);
                            }
                        }
                ));
    }

    private String extractSummary(String rawOutput) {

        if (rawOutput == null || !rawOutput.contains("📌 요약:")) {
            return "요약을 추출할 수 없습니다.";
        }

        String[] lines = rawOutput.split("\n");
        boolean foundSummary = false;
        StringBuilder summaryBuilder = new StringBuilder();

        for (String line : lines) {
            if (foundSummary) {
                if (line.trim().startsWith("=")) break; // 종료 지점
                summaryBuilder.append(line.trim()).append(" ");
            }

            if (line.trim().startsWith("📌 요약:")) {
                foundSummary = true;
            }
        }

        return summaryBuilder.toString().trim();
    }
}