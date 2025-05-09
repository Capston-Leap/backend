package com.dash.leap.global.aimodel.converter;

import com.dash.leap.global.aimodel.exception.EmotionScoreConvertException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Converter
@RequiredArgsConstructor
public class EmotionScoresConverter implements AttributeConverter<Map<String, Double>, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Double> attribute) {
        if (attribute == null || attribute.isEmpty()) return null;

        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new EmotionScoreConvertException("emotionScores 변환 중 오류가 발생했습니다.");
        }
    }

    @Override
    public Map<String, Double> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return new HashMap<>();

        try {
            return objectMapper.readValue(dbData, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new EmotionScoreConvertException("emotionScores 역변환 중 오류가 발생했습니다.");
        }
    }
}
