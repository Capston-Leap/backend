package com.dash.leap.global.openai.client;

import com.dash.leap.global.openai.dto.MessageDto;
import com.dash.leap.global.openai.dto.OpenAIRequest;
import com.dash.leap.global.openai.dto.OpenAIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OpenAIClient {

    private final WebClient webClient;

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    @Value("${spring.ai.openai.chat.options.max-tokens}")
    private int maxTokens;

    public String getGPTResponse(List<MessageDto> messages) {
        OpenAIRequest request = OpenAIRequest.of(model, maxTokens, messages);

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAIResponse.class)
                .map(response -> response.choices().get(0).messageDto().content())
                .block();
    }
}
