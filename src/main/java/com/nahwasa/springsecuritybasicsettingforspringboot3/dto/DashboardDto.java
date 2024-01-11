package com.nahwasa.springsecuritybasicsettingforspringboot3.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.nahwasa.springsecuritybasicsettingforspringboot3.service.FileService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class DashboardDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DogsCountByBreedDto{
        private List<FileService.Elem> currentDayDogs;
        private List<FileService.Elem> previousDayDogs;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class MemberRecentDto{
        private List<MemberOutDto> memberList;

        @Getter
        @Builder
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class MemberOutDto {
            private Long id;
            private String nickname;
            private LocalDate loginTime;
        }
    }
}
