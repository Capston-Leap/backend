package com.dash.leap.admin.information.dto.response;

import com.dash.leap.domain.information.entity.enums.InfoType;

import java.time.LocalDateTime;

public record InformationListResponse(
        Long id,
        InfoType category,
        String title,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}