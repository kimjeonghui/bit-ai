package com.nahwasa.springsecuritybasicsettingforspringboot3.repository;

import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);

    @Query("SELECT count(m) FROM Member m")
    Long countAllMembers();

    @Query("SELECT count(m) FROM Member m WHERE m.updatedAt >= :start AND m.updatedAt < :end")
    Long countMembersByUpdatedAt(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT count(m) FROM Member m WHERE m.createdAt >= :start AND m.createdAt < :end")
    Long countMembersByCreatedAt(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


}
