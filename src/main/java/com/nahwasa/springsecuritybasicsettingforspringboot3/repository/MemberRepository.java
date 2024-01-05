package com.nahwasa.springsecuritybasicsettingforspringboot3.repository;

import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);
}
