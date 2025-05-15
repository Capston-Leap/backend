package com.dash.leap.domain.community.service;

import com.dash.leap.domain.community.dto.request.CommentCreateRequest;
import com.dash.leap.domain.community.dto.response.CommentCreateResponse;
import com.dash.leap.domain.community.dto.response.CommentDeleteResponse;
import com.dash.leap.domain.community.entity.Post;
import com.dash.leap.domain.community.entity.Comment;
import com.dash.leap.domain.community.exception.BadRequestException;
import com.dash.leap.domain.community.exception.ForbiddenException;
import com.dash.leap.domain.community.repository.CommunityRepository;
import com.dash.leap.domain.community.repository.PostRepository;
import com.dash.leap.domain.community.repository.CommentRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.global.auth.user.CustomUserDetails;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 커뮤니티 댓글 생성
    @Transactional
    public CommentCreateResponse create(Long communityId, Long postId, CustomUserDetails userDetails, CommentCreateRequest request) {
        log.info("[CommentService] create() 실행: 커뮤니티에 새로운 댓글을 생성합니다: communityId = {}, postId = {}", communityId, postId);
        User user = userDetails.user();

        communityRepository.findById(communityId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 커뮤니티입니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

        if (!post.getCommunity().getId().equals(communityId)) {
            throw new BadRequestException("해당 커뮤니티에 속한 게시글이 아닙니다.");
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

    // 커뮤니티 댓글 삭제
    @Transactional
    public CommentDeleteResponse delete(Long communityId, Long postId, Long commentId, CustomUserDetails userDetails) {
        log.info("[CommentService] delete() 실행: 댓글을 삭제합니다: communityId = {}, postId = {}, commentId = {}", communityId, postId, commentId);
        User user = userDetails.user();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 댓글입니다."));

        Post post = comment.getPost();

        if (!post.getCommunity().getId().equals(communityId)) {
            throw new BadRequestException("해당 커뮤니티에 속한 게시글이 아닙니다.");
        }

        if (!post.getId().equals(postId)) {
            throw new BadRequestException("해당 게시글에 속한 댓글이 아닙니다.");
        }

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("댓글 작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);

        return new CommentDeleteResponse(comment.getId(), "댓글이 성공적으로 삭제되었습니다.");
    }
}