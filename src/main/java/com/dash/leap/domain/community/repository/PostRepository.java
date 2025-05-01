package com.dash.leap.domain.community.repository;

import com.dash.leap.domain.community.entity.Post;
import com.dash.leap.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByCommunityId(Long communityId, Pageable pageable);
    Page<Post> findAllByCommunityIdAndUserId(Long communityId, Long userId, Pageable pageable);

    long countByUser(User user);
}