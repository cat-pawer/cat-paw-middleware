package com.catpaw.catpawmiddleware.common.converter.security;

import com.catpaw.catpawmiddleware.domain.model.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;
import com.catpaw.catpawmiddleware.domain.eumns.Auth;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;
import com.catpaw.catpawmiddleware.utils.UuidUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

public class NaverMemberContextConverter implements MemberContextConverter {

    private static final String ATTRIBUTE_HOLDER = "response";

    @Override
    public boolean supports(MemberAuthenticationFormFactory form) {
        return SocialType.NAVER.toString().toLowerCase().equals(form.getClientRegistration().getRegistrationId());
    }

    @Override
    public MemberContext converter(MemberAuthenticationFormFactory form) {

        Map<String, Object> attributes = form.getOAuth2User().getAttributes();

        if (attributes == null) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        Object attributeHolder = attributes.get(ATTRIBUTE_HOLDER);

        if (!(attributeHolder instanceof Map<?, ?>)) {
            throw new IllegalArgumentException("사용자 정보를 찾을 수 없습니다.");
        }

        Map<String, Object> attribute = (Map<String, Object>) attributeHolder;

        Object name = attribute.get("name");
        Object email = attribute.get("email");
        Object phone = attribute.get("mobile");

        if (name == null || email == null ||
                !StringUtils.hasText(String.valueOf(name)) ||
                !StringUtils.hasText(String.valueOf(email))) {
            throw new IllegalArgumentException("이름과 이메일은 필수입니다.");
        }

        String strName = String.valueOf(name);
        String strEmail = String.valueOf(email);

        MemberContext memberDto = new MemberContext();
        memberDto.setName(strName);
        memberDto.setEmail(strEmail);
        memberDto.setPassword(UuidUtils.createUuid());
        memberDto.setNickname(strName);
        if (phone != null && StringUtils.hasText(String.valueOf(phone))) memberDto.setPhone(String.valueOf(phone));
        memberDto.setAuth(Auth.MEMBER);
        memberDto.setSocialType(SocialType.NAVER);

        return memberDto;



    }
}
