package com.dash.leap.domain.community.controller;

import com.dash.leap.domain.community.controller.docs.CommunityControllerDocs;
import com.dash.leap.domain.community.dto.request.PostCreateRequest;
import com.dash.leap.domain.community.dto.response.PostCreateResponse;
import com.dash.leap.domain.community.dto.request.PostUpdateRequest;
import com.dash.leap.domain.community.dto.response.PostUpdateResponse;
import com.dash.leap.domain.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class PostController implements CommunityControllerDocs {

    private final PostService postService;

    // 커뮤니티 게시글  생성
    @PostMapping("/{communityId}/post")
    public ResponseEntity<PostCreateResponse> createPost(
            @AuthenticationPrincipal Long userId,
            @PathVariable (name = "communityId") Long communityId,
            @RequestBody PostCreateRequest request
    ) {
        return ResponseEntity.ok(postService.create(communityId, request, userId));
    }

    // 커뮤니티 게시글 수정
    @PatchMapping("/{communityId}/post/{postId}")
    public ResponseEntity<PostUpdateResponse> updatePost(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(
                postService.update(postId, request, userId, communityId)
        );
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/{communityId}/post/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long communityId,
            @PathVariable Long postId,
            @AuthenticationPrincipal Long userId
    ) {
        postService.delete(postId, userId, communityId);
        return ResponseEntity.noContent().build();
    }
}