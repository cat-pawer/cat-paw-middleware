package com.catpaw.catpawmiddleware.domain.entity;

import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    @Column(length = 50)
    @Enumerated(value = EnumType.STRING)
    private Auth auth;

    private String phone;

    private String birth;
}
