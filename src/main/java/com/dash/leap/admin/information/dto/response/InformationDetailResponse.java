package com.dash.leap.admin.information.dto.response;

import com.dash.leap.domain.information.entity.enums.InfoType;

import java.time.LocalDateTime;

public record InformationDetailResponse(
        Long id,
        InfoType category,
        String title,
        String content,
        String url,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}