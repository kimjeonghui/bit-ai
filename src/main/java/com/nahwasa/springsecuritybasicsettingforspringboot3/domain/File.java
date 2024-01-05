package com.nahwasa.springsecuritybasicsettingforspringboot3.domain;

import com.nahwasa.springsecuritybasicsettingforspringboot3.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Table(name = "file")
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;

    private String originPath;
    private String resultPath;
    @ElementCollection
    private List<String> result;

    private Long userId;


    @Builder
    public File(Long userId, String originPath, String resultPath, List<String> result) {
        this.userId = userId;
        this.originPath = originPath;
        this.resultPath = resultPath;
        this.result = result;
    }
}
