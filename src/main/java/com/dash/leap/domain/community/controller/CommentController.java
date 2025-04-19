package com.dash.leap.domain.community.controller;

import com.dash.leap.domain.community.controller.docs.CommentControllerDocs;
import com.dash.leap.domain.community.dto.request.CommentCreateRequest;
import com.dash.leap.domain.community.dto.response.CommentCreateResponse;
import com.dash.leap.domain.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class CommentController implements CommentControllerDocs {

    private final CommentService commentService;

    // 커뮤니티 댓글 생성
    @PostMapping("/{communityId}/post/{postId}/comment")
    public ResponseEntity<CommentCreateResponse> createComment(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long communityId,
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity.ok(commentService.create(userId, communityId, postId, request));
    }
}
