package com.nahwasa.springsecuritybasicsettingforspringboot3.domain;

import com.nahwasa.springsecuritybasicsettingforspringboot3.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class File extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String fileId;

    private String originPath;
    private String resultPath;
    private String result;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @Builder
    public File(Member member, String originPath, String resultPath, String result) {
        this.member = member;
        this.originPath = originPath;
        this.resultPath = resultPath;
        this.result = result;
    }
}
