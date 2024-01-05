package com.nahwasa.springsecuritybasicsettingforspringboot3.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PageInfoDTO {
    private int pageNum;
    private int limit;
    private int totalPages;
    private int totalElements;

    public PageInfoDTO(int pageNum, int limit, int totalPages, int totalElements) {
        this.pageNum = pageNum;
        this.limit = limit;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public static PageInfoDTO of(int pageNum, int limit, int totalPages, int totalElements) {
        return new PageInfoDTO(
                pageNum = pageNum,
                limit = limit,
                totalPages = totalPages,
                totalElements = totalElements
        );
    }
}