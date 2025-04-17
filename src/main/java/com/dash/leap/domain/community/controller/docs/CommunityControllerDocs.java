package com.dash.leap.domain.community.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Community", description = "Community API")
public interface CommunityControllerDocs {

    // 커뮤니티 게시글 삭제
    @Operation(summary = "커뮤니티 게시글 삭제", description = "커뮤니티 내 특정 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "작성자 본인이 아님"),
            @ApiResponse(responseCode = "404", description = "게시글이 존재하지 않음")
    })
    ResponseEntity<Void> deletePost(Long communityId, Long postId, Long userId);
}
