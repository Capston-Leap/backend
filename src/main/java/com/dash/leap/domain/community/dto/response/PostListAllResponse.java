package com.dash.leap.domain.community.dto.response;

import java.time.LocalDate;

public record PostListAllResponse(
        Long postId,
        String nickname,
        LocalDate createdAt,
        String title,
        String content,
        Long commentCount
) {}
