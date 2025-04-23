package com.dash.leap.domain.community.repository;

import com.dash.leap.domain.community.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Long countByPostId(Long postId);
    Page<Comment> findByPostId(Long postId, Pageable pageable);
}