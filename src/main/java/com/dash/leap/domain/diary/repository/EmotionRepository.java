package com.dash.leap.domain.diary.repository;

import com.dash.leap.domain.diary.entity.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long> {

    Optional<Emotion> findByCategory(String emotion);
}