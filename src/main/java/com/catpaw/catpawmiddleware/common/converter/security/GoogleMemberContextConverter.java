package com.catpaw.catpawmiddleware.common.converter.security;

import com.catpaw.catpawmiddleware.domain.security.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;
import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;
import com.catpaw.catpawmiddleware.utils.UuidUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

public class GoogleMemberContextConverter implements MemberContextConverter {

    @Override
    public boolean supports(MemberAuthenticationFormFactory form) {
        return SocialType.GOOGLE.toString().toLowerCase().equals(form.getClientRegistration().getRegistrationId());
    }

    @Override
    public MemberContext converter(MemberAuthenticationFormFactory form) {

        Map<String, Object> attributes = form.getOAuth2User().getAttributes();

        Object name = attributes.get("name");
        Object nickname = attributes.get("given_name");
        Object email = attributes.get("email");

        if (name == null || !StringUtils.hasText(String.valueOf(name))) {
            throw new IllegalArgumentException("이름은 필수값 입니다.");
        }

        if (email == null || !StringUtils.hasText(String.valueOf(email))) {
            throw new IllegalStateException("이메일 정보는 필수 입니다.");
        }

        String strName = String.valueOf(name);
        String strEmail = String.valueOf(email);

        MemberContext memberDto = new MemberContext();
        memberDto.setName(strName);
        memberDto.setEmail(strEmail);
        memberDto.setNickname(nickname == null ? strName : String.valueOf(nickname));
        memberDto.setPassword(UuidUtils.createUuid());
        memberDto.setAuth(Auth.MEMBER);
        memberDto.setSocialType(SocialType.GOOGLE);

        return memberDto;
    }
}
