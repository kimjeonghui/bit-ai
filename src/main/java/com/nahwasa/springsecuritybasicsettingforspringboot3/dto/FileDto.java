package com.nahwasa.springsecuritybasicsettingforspringboot3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {

    private String userId;
    private String originPath;
    private String resultPath;
    private String result;
}
