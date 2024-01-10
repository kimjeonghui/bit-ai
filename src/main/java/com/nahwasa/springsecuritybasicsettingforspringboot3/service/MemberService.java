package com.nahwasa.springsecuritybasicsettingforspringboot3.service;

import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.Member;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.MemberDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = false)
public class MemberService {

    public static final LocalDateTime LOCAL_DATE_TIME = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime();
    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public Long join(String nickname, String pw, Integer role) {
        Member member = Member.createUser(nickname, pw, role);
        validateDuplicateMember(member);
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional(readOnly = false)
    public Member login(String nickname, String pw) throws Exception {
        Optional<Member> byNickname = memberRepository.findByNickname(nickname);
        if(!byNickname.get().getPw().equals(pw)){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        Member member = byNickname.get();
        member.setUpdatedAt(LOCAL_DATE_TIME);
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

    public MemberDto.MemberCountDto countMember(String date) {
        if (date == null) {
            Long count = memberRepository.countAllMembers();
            return MemberDto.MemberCountDto.builder()
                    .count(count)
                    .build();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate findDate = LocalDate.parse(date, formatter);
            LocalDateTime start = findDate.atStartOfDay();
            LocalDateTime end = findDate.plusDays(1).atStartOfDay();

            Long count = memberRepository.countMembersByUpdatedAt(start, end);
            return MemberDto.MemberCountDto.builder()
                    .count(count)
                    .build();
        }
    }

    public MemberDto.TodayJoinMemberCountDto countByCreatedAt() {
        LocalDate date = LocalDate.now();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        Long count = memberRepository.countMembersByCreatedAt(start, end);
        return MemberDto.TodayJoinMemberCountDto.builder()
                .count(count)
                .build();
    }

    public Member updateAnimal(String nickname, Integer animal) throws Exception {
        Optional<Member> member = findOne(nickname);
        if(member.isEmpty())throw  new Exception("멤버가 존재하지 않습니다.");
        Member findedMember = member.get();
        findedMember.setAnimal(animal);
        return memberRepository.save(findedMember);

    }

    public MemberDto.AnimalDto getAnimal(Long memberId) throws Exception {
        Optional<Member> byId = memberRepository.findById(memberId);
        if(byId.isEmpty())throw  new Exception("멤버가 존재하지 않습니다.");
        Member findedMember = byId.get();
        return new MemberDto.AnimalDto(findedMember.getNickname(), findedMember.getAnimal());
    }
}
