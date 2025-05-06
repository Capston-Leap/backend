package com.dash.leap.admin.community.controller.docs;

import com.dash.leap.admin.community.dto.response.CategoryListResponse;
import com.dash.leap.admin.community.dto.response.PostDetailResponse;
import com.dash.leap.admin.community.dto.response.PostListAllResponse;
import com.dash.leap.global.auth.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "[Admin] community", description = "Admin Community API")
public interface AdminCommunityControllerDocs {

    // [관리자] 커뮤니티 카테고리 목록 조회
    @Operation(summary = "카테고리 조회", description = "커뮤니티의 카테고리 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "카테고리 조회 성공")
    ResponseEntity<List<CategoryListResponse>> getCategory();

    // [관리자] 커뮤니티 게시글 전체 목록 조회
    @Operation(summary = "게시글 전체 목록 조회", description = "커뮤니티에 등록된 게시글 전체 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 전체 목록 조회 성공")
    ResponseEntity<Page<PostListAllResponse>> getPostAll(
            @PathVariable(name = "communityId") Long communityId,
            @RequestParam(name = "page", defaultValue = "1") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize
    );

    // [관리자] 커뮤니티 게시글 상세 조회
    @Operation(summary = "게시글 상세 조회", description = "특정 게시글의 상세 정보와 댓글 목록(페이징)을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "게시글 상세 조회 성공")
    ResponseEntity<PostDetailResponse> getPostDetail(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @RequestParam(name = "page", defaultValue = "1") int pageNum,
            @RequestParam(name = "size", defaultValue = "10") int pageSize
    );

    // [관리자] 커뮤니티 게시글 삭제
    @Operation(summary = "게시글 삭제", description = "커뮤니티의 특정 게시글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "게시글 삭제 성공")
    ResponseEntity<Void> deletePost(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            CustomUserDetails userDetails
    );

    // [관리자] 커뮤니티 댓글 삭제
    @Operation(summary = "댓글 삭제", description = "커뮤니티 게시글의 댓글을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "댓글 삭제 성공")
    ResponseEntity<Void> deleteComment(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId,
            CustomUserDetails userDetails
    );
}