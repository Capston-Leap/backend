package com.dash.leap.admin.community.controller;

import com.dash.leap.admin.community.controller.docs.AdminCommunityControllerDocs;
import com.dash.leap.admin.community.dto.response.*;
import com.dash.leap.admin.community.service.AdminCommunityService;
import com.dash.leap.global.auth.user.CustomUserDetails;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/community")
@Validated
public class AdminCommunityController implements AdminCommunityControllerDocs {

    private final AdminCommunityService adminCommunityService;

    // [관리자] 커뮤니티 카테고리 목록 조회
    @GetMapping("")
    public ResponseEntity<List<CategoryListResponse>> getCategory() {
        return ResponseEntity.ok(adminCommunityService.getCategory());
    }

    // [관리자] 커뮤니티 게시글 전체 목록 조회
    @GetMapping("/{communityId}")
    public ResponseEntity<Page<PostListAllResponse>> getPostAll(
            @PathVariable(name = "communityId") Long communityId,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 0) int pageNum,
            @RequestParam(name = "size", defaultValue = "10") @Positive int pageSize
    ) {
        return ResponseEntity.ok(adminCommunityService.getPostAll(communityId, pageNum - 1 , pageSize));
    }

    // [관리자] 커뮤니티 게시글 상세 조회
    @GetMapping("/{communityId}/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "page", defaultValue = "1") @Min(value = 0) int pageNum,
            @RequestParam(name = "size", defaultValue = "10") @Positive int pageSize
    ) {
        return ResponseEntity.ok(adminCommunityService.getPostDetail(communityId, postId, pageNum - 1, pageSize));
    }

    // [관리자] 커뮤니티 게시글 삭제
    @DeleteMapping("/{communityId}/{postId}")
    public ResponseEntity<PostDeleteResponse> deletePost(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        adminCommunityService.delete(communityId, postId, userDetails);
        return ResponseEntity.noContent().build();
    }

    // [관리자] 커뮤니티 댓글 삭제
    @DeleteMapping("/{communityId}/{postId}/{commentId}")
    public ResponseEntity<CommentDeleteResponse> deleteComment(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        adminCommunityService.delete(communityId, postId, commentId, userDetails);
        return ResponseEntity.noContent().build();
    }
}