package com.nahwasa.springsecuritybasicsettingforspringboot3.repository;

import com.nahwasa.springsecuritybasicsettingforspringboot3.common.ResultCount;
import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT f FROM File f WHERE f.updatedAt >= :start AND f.updatedAt < :end")
    List<File> findByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
