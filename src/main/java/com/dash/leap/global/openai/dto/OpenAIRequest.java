package com.dash.leap.global.openai.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record OpenAIRequest(

        String model,
        List<MessageDto> messages,
        int max_tokens

) {
    public static OpenAIRequest of(String model, int maxTokens, String systemPrompt, String userMessage) {
        return OpenAIRequest.builder()
                .model(model)
                .max_tokens(maxTokens)
                .messages(List.of(
                        MessageDto.builder().role("system").content(systemPrompt).build(),
                        MessageDto.builder().role("user").content(userMessage).build()
                ))
                .build();
    }
}
