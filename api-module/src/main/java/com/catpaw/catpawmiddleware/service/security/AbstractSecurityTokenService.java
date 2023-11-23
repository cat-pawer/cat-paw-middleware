package com.catpaw.catpawmiddleware.service.security;

import com.catpaw.catpawmiddleware.common.converter.security.DelegatingMemberContextConverter;
import com.catpaw.catpawcore.domain.eumns.Auth;
import com.catpaw.catpawcore.domain.security.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFactory;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;
import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.security.MemberContexts;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Optional;


@Service
public abstract class AbstractSecurityTokenService {

    private final MemberRepository memberRepository;
    private final MemberAuthenticationFactory memberAuthenticationFactory;

    public AbstractSecurityTokenService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.memberAuthenticationFactory = new MemberAuthenticationFactory(new DelegatingMemberContextConverter());
    }

    protected MemberContexts getMemberContexts(MemberAuthenticationFormFactory form) {
        return this.memberAuthenticationFactory.createMemberContexts(form);
    }

    @Transactional
    protected void processSave(MemberContexts memberContexts) {
        Assert.notNull(memberContexts, "MemberContext 값이 존재하지 않습니다.");

        Optional<Member> member =
                this.memberRepository.findByEmailAndAuth(memberContexts.getEmail(), Auth.MEMBER);
        Member savedMember = member
                .map(value -> this.updateIfNoExisted(memberContexts, value))
                .orElseGet(() -> saveMember(memberContexts));

        memberContexts.updateMember(MemberContext.fromEntity(savedMember));
    }

    private Member updateIfNoExisted(MemberContexts memberContexts, Member savedMember) {
        if (memberContexts.getSocialType() == savedMember.getSocialType()) {
            savedMember.setEmail(memberContexts.getMemberContext().getEmail());
            savedMember.setName(memberContexts.getMemberContext().getName());
            savedMember.setPassword(memberContexts.getMemberContext().getPassword());
            savedMember.setSocialType(memberContexts.getMemberContext().getSocialType());
            savedMember.setAuth(memberContexts.getMemberContext().getAuth());
            return memberRepository.save(savedMember);
        }
        else {
            throw new IllegalStateException("이미 가입된 다른 서비스가 있습니다.");
        }
    }

    private Member saveMember(MemberContexts memberContexts) {
        Member member = new Member();
        member.setName(memberContexts.getUsername());
        member.setNickname(memberContexts.getMemberContext().getNickname());
        member.setAuth(memberContexts.getMemberContext().getAuth());
        member.setPassword(memberContexts.getMemberContext().getPassword());
        member.setSocialType(memberContexts.getMemberContext().getSocialType());

        return memberRepository.save(member);
    }
}
