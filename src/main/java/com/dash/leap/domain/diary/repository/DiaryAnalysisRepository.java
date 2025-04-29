package com.dash.leap.domain.diary.repository;

import com.dash.leap.domain.diary.entity.DiaryAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryAnalysisRepository extends JpaRepository<DiaryAnalysis, Long> {
    Optional<DiaryAnalysis> findByDiaryId(Long diaryId);
}