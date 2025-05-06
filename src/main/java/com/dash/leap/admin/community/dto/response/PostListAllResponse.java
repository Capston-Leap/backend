package com.dash.leap.admin.community.dto.response;

import java.time.LocalDate;

public record PostListAllResponse(
        Long postId,
        Long userId,
        String nickname,
        LocalDate createdAt,
        String title,
        String content,
        Long commentCount
) {}
