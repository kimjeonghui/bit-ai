package com.nahwasa.springsecuritybasicsettingforspringboot3.service;

import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.Member;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.MemberDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(String nickname, String pw, Integer role) {
        Member member = Member.createUser(nickname, pw, role);
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    public Member login(String nickname, String pw) throws Exception {
        Optional<Member> byNickname = memberRepository.findByNickname(nickname);
        if(!byNickname.get().getPw().equals(pw)){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        return byNickname.get();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByNickname(member.getNickname())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    public Optional<Member> findOne(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    public MemberDto.MemberCountDto countMember() {
        Long count = memberRepository.countAllMembers();
        return MemberDto.MemberCountDto.builder()
                .count(count)
                .build();
    }
}
