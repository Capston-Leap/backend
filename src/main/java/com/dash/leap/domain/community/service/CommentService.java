package com.dash.leap.domain.community.service;

import com.dash.leap.domain.community.dto.request.CommentCreateRequest;
import com.dash.leap.domain.community.dto.response.CommentCreateResponse;
import com.dash.leap.domain.community.entity.Post;
import com.dash.leap.domain.community.entity.Comment;
import com.dash.leap.domain.community.repository.PostRepository;
import com.dash.leap.domain.community.repository.CommentRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.global.auth.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 커뮤니티 댓글 생성
    @Transactional
    public CommentCreateResponse create(Long communityId, Long postId, CustomUserDetails userDetails, CommentCreateRequest request) {
        User user = userDetails.user();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (!post.getCommunity().getId().equals(communityId)) {
            throw new IllegalArgumentException("해당 커뮤니티에 속한 게시글이 아닙니다.");
        }

        Comment comment = Comment.builder()
                .content(request.content())
                .post(post)
                .user(user)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return new CommentCreateResponse(
                savedComment.getId(),
                savedComment.getContent(),
                "댓글이 성공적으로 등록되었습니다."
        );
    }
}