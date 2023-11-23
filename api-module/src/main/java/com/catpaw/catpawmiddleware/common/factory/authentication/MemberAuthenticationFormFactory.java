package com.catpaw.catpawmiddleware.common.factory.authentication;

import com.catpaw.catpawcore.domain.entity.Member;
import lombok.Getter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

@Getter
public class MemberAuthenticationFormFactory {

    private OAuth2User oAuth2User;

    private ClientRegistration clientRegistration;

    private Member member;

    private MemberAuthenticationFormFactory() {}

    public static MemberAuthenticationFormFactory createLocalMemberForm(Member member) {
        Assert.notNull(member, "member 값이 존재하지 않습니다.");

        MemberAuthenticationFormFactory form = new MemberAuthenticationFormFactory();
        form.member = member;

        return form;
    }

    public static MemberAuthenticationFormFactory createOAuthMemberForm(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        Assert.notNull(oAuth2User, "oAuth2User 값이 존재하지 않습니다.");
        Assert.notNull(clientRegistration, "clientRegistration 값이 존재하지 않습니다.");

        MemberAuthenticationFormFactory form = new MemberAuthenticationFormFactory();
        form.oAuth2User = oAuth2User;
        form.clientRegistration = clientRegistration;

        return form;
    }
}
