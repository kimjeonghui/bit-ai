package com.nahwasa.springsecuritybasicsettingforspringboot3.service;

import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.Member;
import com.nahwasa.springsecuritybasicsettingforspringboot3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository repository;



    public Long join(String nickname, String pw, Integer role) {
        Member member = Member.createUser(nickname, pw, role);
        validateDuplicateMember(member);
        repository.save(member);

        return member.getId();
    }

    public Member login(String nickname, String pw) throws Exception {
        Optional<Member> byNickname = repository.findByNickname(nickname);
        if(!byNickname.get().getPw().equals(pw)){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        return byNickname.get();
    }

    private void validateDuplicateMember(Member member) {
        repository.findByNickname(member.getNickname())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    public Optional<Member> findOne(String nickname) {
        return repository.findByNickname(nickname);
    }
}
