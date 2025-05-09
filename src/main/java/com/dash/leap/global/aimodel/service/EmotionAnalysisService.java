package com.dash.leap.global.aimodel.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class EmotionAnalysisService {

    @Value("${ai.emotion.python-path}")
    private String pythonPath;

    @Value("${ai.emotion.sa-path}")
    private String scriptPath;

    public String analyzeEmotion(String text) {

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(pythonPath, scriptPath, text);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {

                String output = reader.lines().collect(Collectors.joining("\n"));
                int exitCode = process.waitFor();

                if (exitCode != 0) {
                    log.error("[EmotionAnalysisService] Emotion analysis failed: {}", output);
                    log.error("[EmotionAnalysisService] Emotion analysis exited with code {}", exitCode);
                    return "AI 감정 분석 실패: " + output;
                }

                return output;
            }
        } catch (Exception e) {
            log.error("[EmotionAnalysisService] Emotion analysis error ", e);
            return "AI 감정 분석 중 오류 발생: " + e.getMessage();
        }
    }
}
