package com.dash.leap.domain.diary.repository;

import com.dash.leap.domain.diary.entity.Diary;
import com.dash.leap.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT d FROM Diary d WHERE YEAR(d.createdAt) = :year AND MONTH(d.createdAt) = :month AND d.user.id = :userId")
    List<Diary> findByYearAndMonthAndUserId(@Param("year") int year, @Param("month") int month, @Param("userId") Long userId);

    boolean existsByUserAndCreatedAtBetween(User user, LocalDateTime startOfDay, LocalDateTime endOfDay);
}