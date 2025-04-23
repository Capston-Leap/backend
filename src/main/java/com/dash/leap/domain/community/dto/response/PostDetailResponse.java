package com.dash.leap.domain.community.dto.response;

import org.springframework.data.domain.Page;

import java.time.LocalDate;

public record PostDetailResponse(
        Long postId,
        Long userId,
        String nickname,
        LocalDate createdAt,
        String title,
        String content,
        Long commentCount,
        Page<CommentList> comments
) {
    public record CommentList(
            Long commentId,
            Long userId,
            String nickname,
            LocalDate createdAt,
            String content
    ) {}
}
