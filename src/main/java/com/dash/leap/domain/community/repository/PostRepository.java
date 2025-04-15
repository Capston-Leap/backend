package com.dash.leap.domain.community.repository;

import com.dash.leap.domain.community.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // 현재는 단순 저장용
}