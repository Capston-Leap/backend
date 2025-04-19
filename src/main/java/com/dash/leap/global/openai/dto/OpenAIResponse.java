package com.dash.leap.global.openai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OpenAIResponse(
        List<Choice> choices
) {

    public record Choice(
            @JsonProperty("message")
            MessageDto messageDto
    ) {}
}
