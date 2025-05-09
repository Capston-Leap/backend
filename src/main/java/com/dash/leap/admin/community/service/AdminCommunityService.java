package com.dash.leap.admin.community.service;

import com.dash.leap.admin.community.dto.response.*;
import com.dash.leap.domain.community.entity.Comment;
import com.dash.leap.domain.community.entity.Post;
import com.dash.leap.admin.community.exception.ForbiddenException;
import com.dash.leap.domain.community.repository.CommentRepository;
import com.dash.leap.domain.community.repository.CommunityRepository;
import com.dash.leap.domain.community.repository.PostRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.domain.user.entity.enums.UserType;
import com.dash.leap.global.auth.user.CustomUserDetails;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.dash.leap.admin.community.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCommunityService {

    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // [관리자] 커뮤니티 카테고리 목록 조회
    @Transactional(readOnly = true)
    public List<CategoryListResponse> getCategory() {
        return communityRepository.findAll().stream()
                .map(c -> new CategoryListResponse(c.getId(), c.getCommunityType()))
                .toList();
    }

    // [관리자] 커뮤니티 게시글 전체 목록 조회
    @Transactional(readOnly = true)
    public Page<PostListAllResponse> getPostAll(Long communityId, int page, int size) {
        communityRepository.findById(communityId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 커뮤니티입니다."));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postPage = postRepository.findAllByCommunityId(communityId, pageable);

        return postPage.map(post -> new PostListAllResponse(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getNickname(),
                post.getCreatedAt().toLocalDate(),
                post.getTitle(),
                post.getContent(),
                commentRepository.countByPostId(post.getId())
        ));
    }

    // [관리자] 커뮤니티 게시글 상세 조회
    @Transactional(readOnly = true)
    public PostDetailResponse getPostDetail(Long communityId, Long postId, int page, int size) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

        if (!post.getCommunity().getId().equals(communityId)) {
            throw new BadRequestException("해당 커뮤니티에 속한 게시글이 아닙니다.");
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Comment> commentPage = commentRepository.findByPostId(postId, pageable);

        Page<PostDetailResponse.CommentList> comments = commentPage.map(comment ->
                new PostDetailResponse.CommentList(
                        comment.getId(),
                        comment.getUser().getId(),
                        comment.getUser().getNickname(),
                        comment.getCreatedAt().toLocalDate(),
                        comment.getContent()
                )
        );

        return new PostDetailResponse(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getNickname(),
                post.getCreatedAt().toLocalDate(),
                post.getTitle(),
                post.getContent(),
                commentPage.getTotalElements(),
                comments
        );
    }

    // [관리자] 커뮤니티 게시글 삭제
    @Transactional
    public PostDeleteResponse delete(Long communityId, Long postId, CustomUserDetails userDetails) {
        User user = userDetails.user();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

        if (!post.getCommunity().getId().equals(communityId)) {
            throw new BadRequestException("해당 커뮤니티에 속한 게시글이 아닙니다.");
        }

        if (user.getUserType() != UserType.ADMIN) {
            throw new ForbiddenException("관리자만 게시글을 삭제할 수 있습니다.");
        }

        postRepository.delete(post);

        return new PostDeleteResponse(postId, "게시글이 성공적으로 삭제되었습니다.");
    }

    // [관리자] 커뮤니티 댓글 삭제
    @Transactional
    public CommentDeleteResponse delete(Long communityId, Long postId, Long commentId, CustomUserDetails userDetails) {
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

        if (user.getUserType() != UserType.ADMIN) {
            throw new ForbiddenException("관리자만 댓글을 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);

        return new CommentDeleteResponse(commentId, "댓글이 성공적으로 삭제되었습니다.");
    }
}