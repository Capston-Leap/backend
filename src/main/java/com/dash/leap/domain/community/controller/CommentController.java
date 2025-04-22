package com.dash.leap.domain.community.controller;

import com.dash.leap.domain.community.controller.docs.CommentControllerDocs;
import com.dash.leap.domain.community.dto.request.CommentCreateRequest;
import com.dash.leap.domain.community.dto.response.CommentCreateResponse;
import com.dash.leap.domain.community.service.CommentService;
import com.dash.leap.global.auth.user.CustomUserDetails;
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
            @PathVariable Long communityId,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody CommentCreateRequest request
    ) {
        return ResponseEntity.ok(commentService.create(communityId, postId, userDetails, request));
    }

    // 커뮤니티 댓글 삭제
    @DeleteMapping("/{communityId}/post/{postId}/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long communityId,
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        commentService.delete(communityId, postId, commentId, userDetails);
        return ResponseEntity.noContent().build();
    }
}