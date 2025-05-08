package com.dash.leap.domain.community.service;

import com.dash.leap.domain.community.dto.request.PostCreateRequest;
import com.dash.leap.domain.community.dto.response.*;
import com.dash.leap.domain.community.dto.request.PostUpdateRequest;
import com.dash.leap.domain.community.entity.Comment;
import com.dash.leap.domain.community.entity.Community;
import com.dash.leap.domain.community.entity.Post;
import com.dash.leap.domain.community.exception.ForbiddenException;
import com.dash.leap.domain.community.repository.CommentRepository;
import com.dash.leap.domain.community.repository.CommunityRepository;
import com.dash.leap.domain.community.repository.PostRepository;
import com.dash.leap.domain.user.entity.User;
import com.dash.leap.global.auth.user.CustomUserDetails;
import com.dash.leap.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import com.dash.leap.domain.community.exception.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 커뮤니티 게시글 전체 목록 조회
    @Transactional(readOnly = true)
    public Page<PostListAllResponse> getPostAll(Long communityId, int page, int size) {
        communityRepository.findById(communityId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 커뮤니티입니다."));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postPage = postRepository.findAllByCommunityId(communityId, pageable);

        return postPage.map(post -> new PostListAllResponse(
                post.getId(),
                post.getUser().getNickname(),
                post.getCreatedAt().toLocalDate(),
                post.getTitle(),
                post.getContent(),
                commentRepository.countByPostId(post.getId())
        ));
    }

    // 커뮤니티 게시글 상세 조회
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

    // 마이페이지 - 본인이 작성한 커뮤니티 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<PostListAllResponse> getMyPostAll(Long communityId, CustomUserDetails userDetails, int page, int size) {

        Long userId = userDetails.user().getId();

        communityRepository.findById(communityId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 커뮤니티입니다."));

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> postPage = postRepository.findAllByCommunityIdAndUserId(communityId, userId, pageable);

        return postPage.map(post -> new PostListAllResponse(
                post.getId(),
                post.getUser().getNickname(),
                post.getCreatedAt().toLocalDate(),
                post.getTitle(),
                post.getContent(),
                commentRepository.countByPostId(post.getId())
        ));
    }

    // 커뮤니티 게시글 생성
    @Transactional
    public PostCreateResponse create(Long communityId, CustomUserDetails userDetails, PostCreateRequest request) {
        User user = userDetails.user();

        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 커뮤니티입니다."));

        Post post = Post.builder()
                .user(user)
                .community(community)
                .title(request.title())
                .content(request.content())
                .build();

        Post savedPost = postRepository.save(post);

        return new PostCreateResponse(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                "게시글이 커뮤니티에 성공적으로 등록되었습니다."
        );
    }

    // 커뮤니티 게시글 수정
    @Transactional
    public PostUpdateResponse update(Long communityId, Long postId, CustomUserDetails userDetails, PostUpdateRequest request) {
        User user = userDetails.user();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 커뮤니티입니다."));

        if (!post.getCommunity().getId().equals(communityId)) {
            throw new BadRequestException("해당 커뮤니티에 속한 게시글이 아닙니다.");
        }

        if (!post.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("게시글 작성자만 수정할 수 있습니다.");
        }

        post.update(request.title(), request.content());

        return new PostUpdateResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                "게시글이 성공적으로 수정되었습니다."
        );
    }

    // 커뮤니티 게시글 삭제
    @Transactional
    public PostDeleteResponse delete(Long communityId, Long postId, CustomUserDetails userDetails) {
        User user = userDetails.user();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 게시글입니다."));

        if (!post.getCommunity().getId().equals(communityId)) {
            throw new BadRequestException("해당 커뮤니티에 속한 게시글이 아닙니다.");
        }

        if (!post.getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("게시글 작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);

        return new PostDeleteResponse(post.getId(), "게시글이 성공적으로 삭제되었습니다.");
    }
}