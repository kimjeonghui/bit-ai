package com.nahwasa.springsecuritybasicsettingforspringboot3.dto;

import com.nahwasa.springsecuritybasicsettingforspringboot3.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class FileDto  {

    private Long userId;
    private String originPath;
    private String resultPath;
    private List<String> result;
    private LocalDateTime createdAt;
}
