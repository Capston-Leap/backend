package com.dash.leap.domain.diary.repository;

import com.dash.leap.domain.diary.entity.DiaryAnalysis;
import com.dash.leap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryAnalysisRepository extends JpaRepository<DiaryAnalysis, Long> {
    Optional<DiaryAnalysis> findByDiaryId(Long diaryId);

    List<DiaryAnalysis> findByDiary_UserAndAnalysisTimeBetween(User user, LocalDateTime start, LocalDateTime end);
}