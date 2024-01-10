package com.nahwasa.springsecuritybasicsettingforspringboot3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;


public class MemberDto {

    @Getter
    @Setter
    public static class joinPost {

        private Integer role;
        private String nickname;
        private String pw;
    }

    @Getter
    @Setter
    public static class loginPost {
        private String nickname;
        private String password;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class loginResponse {
        private Integer role;
        private Long userId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MemberCountDto{
        private Long count;
    }
}
