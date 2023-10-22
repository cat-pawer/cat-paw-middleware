package com.catpaw.catpawmiddleware.domain.model;

import com.catpaw.catpawmiddleware.domain.entity.Member;
import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberContext {

    private Long id;

    private String name;

    private String nickname;

    private String email;

    private String password;

    private SocialType socialType;

    private Auth auth;

    private String phone;

    private String birth;

    public static MemberContext fromEntity(Member member) {

        MemberContext memberContext = new MemberContext();
        return memberContext;
    }
}
