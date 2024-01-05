package com.nahwasa.springsecuritybasicsettingforspringboot3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class MemberDto {

    @Getter
    @Setter
    public static class joinPost{

        private Integer role;
        private String nickname;
        private String pw;
    }
    @Getter
    @Setter
   public static class loginPost{
        private String nickname;
        private String password;
   }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
   public static class loginResponse{
        private Integer role;
        private Long userId;
   }
}
