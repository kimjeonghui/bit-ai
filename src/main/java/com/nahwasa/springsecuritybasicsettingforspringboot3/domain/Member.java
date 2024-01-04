package com.nahwasa.springsecuritybasicsettingforspringboot3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userid;

    private String pw;

    private String roles;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<File> files;

    private Member(Long id, String userid, String pw, String roleUser) {
        this.id = id;
        this.userid = userid;
        this.pw = pw;
        this.roles = roleUser;
    }

    protected Member() {}

    public static Member createUser(String userId, String pw, PasswordEncoder passwordEncoder) {
        return new Member(null, userId, passwordEncoder.encode(pw), "USER");
    }

}
