package com.dash.leap.domain.chat.service.prompt;

import com.dash.leap.domain.diary.entity.DiaryAnalysis;
import com.dash.leap.domain.user.entity.enums.ChatbotType;

import java.util.List;
import java.util.Map;

public class SystemPromptFactory {

    public static String getSystemPrompt(ChatbotType type, List<DiaryAnalysis> recentDiaryAnal) {

        // 공통 프롬프트
        StringBuilder prompt = new StringBuilder("""
            너의 이름은 '리피'야.
            이 앱의 사용자는 '자립준비청년'이야.
            너는 자립준비청년을 위한 챗봇이야.
            아래는 사용자의 최근 감정일기 분석 내용이야.
            이 정보를 바탕으로 사용자의 감정을 공감하고, 성격에 맞게 대화를 이끌어줘.
            너무 무겁지 않게, 친구처럼 다정하게 말해줘.
            \n
        """);

        // 요약 문장 추가
        if (!recentDiaryAnal.isEmpty()) {
            prompt.append("[감정일기 요약]\n");
            for (DiaryAnalysis analysis : recentDiaryAnal) {
                prompt.append("- ").append(analysis.getSummary()).append("\n");
            }

            prompt.append("\n[최근 감정 점수 분석]\n");

            // 최신 감정 점수 기준 (ex. 오늘 or 어제 중 마지막 분석)
            Map<String, Double> scores = recentDiaryAnal.get(recentDiaryAnal.size() - 1).getEmotionScore();

            scores.entrySet().stream()
                    .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                    .forEach(e -> {
                        String emotionName = e.getKey();
                        double score = Math.round(e.getValue() * 10.0) / 10.0; // 소수점 한 자리 반올림
                        prompt.append("- ").append(emotionName).append(": ").append(score).append("\n");
                    });
        } else {
            prompt.append("사용자의 최근 감정일기 분석 정보가 없어.\n");
        }

        // 성격별 추가 프롬프트
        prompt.append("\n[리피의 성격 정보]\n");
        prompt.append(switch (type) {
            case FF -> """
                너는 여자이고, 따뜻한 조언자 같은 친구야.
                공감 능력이 뛰어나고, 다정한 말투를 사용해.
                상대가 지친 날엔 위로를, 평범한 날엔 격려를 해줘.
            """;
            case FT -> """
                너는 여자이고, 현실적인 멘토 같은 친구야.
                감정은 존중하되, 해결책 중심으로 이야기해줘.
                실질적인 조언을 제공하면서 동기를 부여해줘.
            """;
            case MF -> """
                너는 남자이고, 긍정 에너자이저 같은 친구야.
                분위기를 유쾌하게 전환시켜주는 밝은 성격이야.
                너무 무겁지 않게 말하면서도, 상대의 감정을 배려해줘.
            """;
            case MT -> """
                너는 남자이고, 믿음직한 형 또는 오빠 같은 친구야.
                조용하지만 단단한 말투로 상대를 안심시켜줘.
                차분하게 조언하면서 안정감을 줘야 해.
            """;
        });

        return prompt.toString();
    }
}
