package com.catpaw.catpawmiddleware.service.security;


import com.catpaw.catpawmiddleware.common.converter.DelegatingMemberContextConverter;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFactory;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;
import com.catpaw.catpawmiddleware.domain.entity.Member;
import com.catpaw.catpawmiddleware.domain.member.MemberContexts;
import com.catpaw.catpawmiddleware.domain.member.MemberContextsImpl;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class SecurityTokenService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberAuthenticationFactory memberAuthenticationFactory;

    public SecurityTokenService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.memberAuthenticationFactory = new MemberAuthenticationFactory(new DelegatingMemberContextConverter());
    }

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        if (!StringUtils.hasText(memberId)) {
            throw new IllegalArgumentException("잘못된 토큰 정보입니다.");
        }

        try {
            Member member = memberRepository
                    .findById(Long.valueOf(memberId))
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            MemberContexts memberContexts =
                    this.memberAuthenticationFactory
                            .createMemberContexts(MemberAuthenticationFormFactory.createLocalMemberForm(member));

            return new MemberContextsImpl(memberContexts);
        } catch (NumberFormatException e) {
            throw new InsufficientAuthenticationException("잘못된 토근 정보입니다.");
        }
    }
}
