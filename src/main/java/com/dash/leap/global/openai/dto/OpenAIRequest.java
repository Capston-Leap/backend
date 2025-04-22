package com.dash.leap.global.openai.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OpenAIRequest(

        String model,
        List<MessageDto> messages,
        int max_tokens

) {
    public static OpenAIRequest of(String model, int maxTokens, List<MessageDto> messages) {
        return OpenAIRequest.builder()
                .model(model)
                .max_tokens(maxTokens)
                .messages(messages)
                .build();
    }
}
