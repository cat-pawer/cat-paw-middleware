package com.catpaw.catpawmiddleware.common.converter;

import com.catpaw.catpawmiddleware.domain.model.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;
import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;
import com.catpaw.catpawmiddleware.utils.UuidUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

public class GoogleMemberPrincipalConverter implements MemberPrincipalConverter {

    @Override
    public boolean supports(MemberAuthenticationFormFactory form) {
        return SocialType.GOOGLE.toString().equals(form.getClientRegistration().getRegistrationId());
    }

    @Override
    public MemberContext converter(MemberAuthenticationFormFactory form) {

        Map<String, Object> attributes = form.getOAuth2User().getAttributes();

        String name = (String) attributes.get("name");
        if (!StringUtils.hasText(name)) {
            name = UuidUtils.createUuid();
        }

        String email = (String) attributes.get("email");
        if (!StringUtils.hasText(email)) {
            throw new IllegalStateException("이메일 정보는 필수 입니다.");
        }

        MemberContext memberDto = new MemberContext();
        memberDto.setName(name);
        memberDto.setEmail(email);
        memberDto.setAuth(Auth.MEMBER);
        memberDto.setSocialType(SocialType.GOOGLE);

        return memberDto;
    }
}
