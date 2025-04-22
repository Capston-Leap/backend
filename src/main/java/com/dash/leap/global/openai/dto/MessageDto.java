package com.dash.leap.global.openai.dto;

import lombok.Builder;

@Builder
public record MessageDto(

        String role,
        String content
) {
}
