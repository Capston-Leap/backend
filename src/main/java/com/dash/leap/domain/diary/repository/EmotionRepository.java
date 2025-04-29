package com.dash.leap.domain.diary.repository;

import com.dash.leap.domain.diary.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long> {
}