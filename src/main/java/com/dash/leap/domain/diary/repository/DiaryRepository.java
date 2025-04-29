package com.dash.leap.domain.diary.repository;

import com.dash.leap.domain.diary.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    @Query("SELECT d FROM Diary d WHERE YEAR(d.createdAt) = :year AND MONTH(d.createdAt) = :month")
    List<Diary> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
}