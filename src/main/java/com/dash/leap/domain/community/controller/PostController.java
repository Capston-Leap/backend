package com.dash.leap.domain.community.controller;

import com.dash.leap.domain.community.controller.docs.PostControllerDocs;
import com.dash.leap.domain.community.dto.response.PostDetailResponse;
import com.dash.leap.domain.community.dto.response.PostListAllResponse;
import com.dash.leap.domain.community.dto.request.PostCreateRequest;
import com.dash.leap.domain.community.dto.response.PostCreateResponse;
import com.dash.leap.domain.community.dto.request.PostUpdateRequest;
import com.dash.leap.domain.community.dto.response.PostUpdateResponse;
import com.dash.leap.domain.community.service.PostService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
public class PostController implements PostControllerDocs {

    private final PostService postService;

    // 커뮤니티 게시글 전체 목록 조회
    @GetMapping("/{communityId}/post")
    public ResponseEntity<Page<PostListAllResponse>> getPostAll(
            @PathVariable Long communityId,
            @RequestParam(name = "page", defaultValue = "1") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(postService.getPostAll(communityId, pageNum - 1 , pageSize));
    }

    // 커뮤니티 게시글 상세 조회
    @GetMapping("/{communityId}/post/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(
            @PathVariable Long communityId,
            @PathVariable Long postId,
            @RequestParam(name = "page", defaultValue = "1") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize
    ) {
        return ResponseEntity.ok(postService.getPostDetail(communityId, postId, pageNum - 1, pageSize));
    }

    // 커뮤니티 게시글 생성
    @PostMapping("/{communityId}/post")
    public ResponseEntity<PostCreateResponse> createPost(
            @PathVariable(name = "communityId") Long communityId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PostCreateRequest request
    ) {
        return ResponseEntity.ok(postService.create(communityId, userDetails, request));
    }

    // 커뮤니티 게시글 수정
    @PatchMapping("/{communityId}/post/{postId}")
    public ResponseEntity<PostUpdateResponse> updatePost(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody PostUpdateRequest request
    ) {
        return ResponseEntity.ok(
                postService.update(communityId, postId, userDetails, request )
        );
    }

    // 커뮤니티 게시글 삭제
    @DeleteMapping("/{communityId}/post/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long communityId,
            @PathVariable Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        postService.delete(communityId, postId, userDetails);
        return ResponseEntity.noContent().build();
    }
}