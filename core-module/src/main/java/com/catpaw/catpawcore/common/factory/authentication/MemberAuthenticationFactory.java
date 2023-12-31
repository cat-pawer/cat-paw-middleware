package com.catpaw.catpawcore.common.factory.authentication;

import com.catpaw.catpawcore.common.converter.security.DelegatingMemberContextConverter;
import com.catpaw.catpawcore.domain.security.MemberContext;
import com.catpaw.catpawcore.domain.security.GoogleMember;
import com.catpaw.catpawcore.domain.security.LocalMember;
import com.catpaw.catpawcore.domain.security.MemberContexts;
import com.catpaw.catpawcore.domain.security.NaverMember;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class MemberAuthenticationFactory {

    private final DelegatingMemberContextConverter converter;

    public MemberAuthenticationFactory(DelegatingMemberContextConverter converter) {
        this.converter = converter;
    }

    public MemberContexts createMemberContexts(MemberAuthenticationFormFactory form) {
        MemberContext memberContext = this.converter.convert(form);

        if (form.getClientRegistration() == null) {
            return new LocalMember(memberContext, List.of(new SimpleGrantedAuthority(memberContext.getAuth().toString())));
        }

        return switch (form.getClientRegistration().getRegistrationId()) {
            case "google" -> new GoogleMember(memberContext, form.getOAuth2User().getAuthorities(), form.getOAuth2User().getAttributes());
            case "naver" -> new NaverMember(memberContext, form.getOAuth2User().getAuthorities(), form.getOAuth2User().getAttributes());
            default -> throw new IllegalArgumentException("지원하지 않는 타입입니다.");
        };
    }
}
