package com.nahwasa.springsecuritybasicsettingforspringboot3.domain;

import com.nahwasa.springsecuritybasicsettingforspringboot3.common.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Table(name = "member")
@ToString
public class Member extends TimeBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nickname;

    private String pw;

    private String role;

    private Integer animal;

    private Member(Long id, String nickname, String pw, String role) {
        this.id = id;
        this.nickname = nickname;
        this.pw = pw;
        this.role = role;
    }

    protected Member() {}

    public static Member createUser(String nickname, String pw, Integer role) {
        if(role.equals(Integer.valueOf(1))){
            return new Member(null, nickname, pw, "ADMIN");
        }else{
            return new Member(null, nickname, pw,"USER");
        }
    }

}
