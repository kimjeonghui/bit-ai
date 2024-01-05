package com.nahwasa.springsecuritybasicsettingforspringboot3.controller;

import com.nahwasa.springsecuritybasicsettingforspringboot3.domain.Member;
import com.nahwasa.springsecuritybasicsettingforspringboot3.dto.MemberDto;
import com.nahwasa.springsecuritybasicsettingforspringboot3.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class AuthorizationController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public HttpStatus join(@RequestBody MemberDto.joinPost dto) {
        System.out.println("멤버 생성");
        try {
            memberService.join(dto.getNickname(), dto.getPw(), dto.getRole());
            return HttpStatus.OK;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return HttpStatus.BAD_REQUEST;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.loginPost dto) {
        try {
            Member login = memberService.login(dto.getNickname(), dto.getPassword());
            MemberDto.loginResponse response;

            if (login.getRole().equals("ADMIN")) {
                response = new MemberDto.loginResponse(1, login.getId());
            } else {
                response = new MemberDto.loginResponse(2, login.getId());
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);

        } catch (Exception e) {
            // 클라이언트에 오류 메시지를 포함한 상태로 응답
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Login failed: " + e.getMessage());
        }

    }



}
