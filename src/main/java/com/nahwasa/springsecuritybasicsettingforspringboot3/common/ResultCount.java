package com.nahwasa.springsecuritybasicsettingforspringboot3.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResultCount {
    private List<String> result;
    private Long count;

    public ResultCount(List<String> result, Long count) {
        this.result = result;
        this.count = count;
    }
}
