package com.dash.leap.domain.community.controller.docs;

import com.dash.leap.domain.community.dto.request.CommentCreateRequest;
import com.dash.leap.domain.community.dto.response.CommentCreateResponse;
import com.dash.leap.global.auth.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Community-Comment", description = "Community-Comment API")
public interface CommentControllerDocs {

    // 커뮤니티 댓글 생성
    @Operation(summary = "댓글 생성", description = "커뮤니티 게시글에 댓글을 작성합니다.")
    @ApiResponse(responseCode = "201", description = "댓글 생성 성공")
    ResponseEntity<CommentCreateResponse> createComment(
            @PathVariable(name = "communityId") Long communityId,
            @PathVariable(name = "postId") Long postId,
            CustomUserDetails userDetails,
            @RequestBody CommentCreateRequest request
    );
}