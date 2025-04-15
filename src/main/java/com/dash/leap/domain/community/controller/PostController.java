package com.dash.leap.domain.community.controller;

import com.dash.leap.domain.community.dto.request.PostCreateRequest;
import com.dash.leap.domain.community.dto.response.PostCreateResponse;
import com.dash.leap.domain.community.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class PostController {

    private final PostService postService;

    @PostMapping("/{communityId}/post")
    public ResponseEntity<PostCreateResponse> createPost(
            @PathVariable Long communityId,
            @RequestBody PostCreateRequest request
    ) {
        return ResponseEntity.ok(postService.create(communityId, request));
    }
}