package com.nahwasa.springsecuritybasicsettingforspringboot3.controller;

import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.MemberDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.ResponseDTO;
import com.nahwasa.springsecuritybasicsettingforspringboot3.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashboardController {

    private final MemberService memberService;

    @GetMapping("/members/count")
    public ResponseEntity<?> userCount() {
        MemberDto.MemberCountDto memberCountDto = memberService.countMember();
        ResponseDTO responseBody = new ResponseDTO(HttpStatus.OK, "성공", memberCountDto);
        return ResponseEntity.ok(responseBody);
    }
}
