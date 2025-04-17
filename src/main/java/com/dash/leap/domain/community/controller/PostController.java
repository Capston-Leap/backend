package com.dash.leap.domain.community.controller;

import com.dash.leap.domain.community.dto.request.PostCreateRequest;
import com.dash.leap.domain.community.dto.response.PostCreateResponse;
import com.dash.leap.domain.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class PostController {

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
}