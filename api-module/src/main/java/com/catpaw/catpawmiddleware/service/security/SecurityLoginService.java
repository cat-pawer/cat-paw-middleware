package com.catpaw.catpawmiddleware.service.security;

import com.catpaw.catpawcore.common.factory.authentication.MemberAuthenticationFormFactory;
import com.catpaw.catpawcore.domain.security.MemberContexts;
import com.catpaw.catpawcore.domain.security.MemberContextsImpl;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


@Service
public class SecurityLoginService extends AbstractSecurityTokenService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    public SecurityLoginService(MemberRepository memberRepository) {
        super(memberRepository);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuthUserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuthUserService.loadUser(userRequest);

        MemberAuthenticationFormFactory form = MemberAuthenticationFormFactory.createOAuthMemberForm(oAuth2User, clientRegistration);
        MemberContexts memberContexts = super.getMemberContexts(form);

        super.processSave(memberContexts);

        return new MemberContextsImpl(memberContexts);
    }
}
