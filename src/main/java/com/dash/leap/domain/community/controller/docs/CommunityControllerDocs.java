package com.dash.leap.domain.community.controller.docs;

import com.dash.leap.domain.community.dto.request.PostCreateRequest;
import com.dash.leap.domain.community.dto.request.PostUpdateRequest;
import com.dash.leap.domain.community.dto.response.PostCreateResponse;
import com.dash.leap.domain.community.dto.response.PostUpdateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Community", description = "Community API")
public interface CommunityControllerDocs {

    // 커뮤니티 게시글 생성
    @Operation(summary = "게시글 생성", description = "커뮤니티에 게시글을 작성합니다.")
    @ApiResponse(responseCode = "201", description = "게시글 생성 성공")
    @PostMapping("/{communityId}/post")
    ResponseEntity<PostCreateResponse> createPost(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "communityId") Long communityId,
            @RequestBody PostCreateRequest request
    );

    // 커뮤니티 게시글 수정
    @Operation(summary = "게시글 수정", description = "커뮤니티의 특정 게시글을 수정합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 수정 성공")
    @PatchMapping("/{communityId}/post/{postId}")
    ResponseEntity<PostUpdateResponse> updatePost(
            @AuthenticationPrincipal Long userId,
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @RequestBody PostUpdateRequest request
    );

    // 커뮤니티 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "커뮤니티의 특정 게시글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "게시글 삭제 성공")
    @DeleteMapping("/{communityId}/post/{postId}")
    ResponseEntity<Void> deletePost(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @AuthenticationPrincipal Long userId
    );
}