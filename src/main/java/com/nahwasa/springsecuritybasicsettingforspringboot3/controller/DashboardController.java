package com.nahwasa.springsecuritybasicsettingforspringboot3.controller;

import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.DashboardDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.MemberDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.ResponseDTO;
import com.nahwasa.springsecuritybasicsettingforspringboot3.service.FileService;
import com.nahwasa.springsecuritybasicsettingforspringboot3.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final MemberService memberService;
    private final FileService fileService;

    @GetMapping("/members/count")
    public ResponseEntity<?> userCount(@RequestParam(required = false) String date) {
        MemberDto.MemberCountDto memberCountDto = memberService.countMember(date);
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", memberCountDto);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/members/today/count")
    public ResponseEntity<?> getTodayMemberCount() {
        MemberDto.TodayJoinMemberCountDto todayJoinMemberCountDto = memberService.countByCreatedAt();
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", todayJoinMemberCountDto);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/dogs/count")
    public ResponseEntity<?> dogsCountByBreed(@RequestParam(required = false) String date) {
        DashboardDto.DogsCountByBreedDto dogsCountByBreedDto = fileService.dogsCountByBreed(date);
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", dogsCountByBreedDto);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/members/recent")
    public ResponseEntity<?> recentLoginMemberCount(@RequestParam(defaultValue = "10") Integer limit) {
        DashboardDto.MemberRecentDto memberRecentDto = memberService.recentLoginMember(limit);
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", memberRecentDto);
        return ResponseEntity.ok(responseBody);
    }
}
