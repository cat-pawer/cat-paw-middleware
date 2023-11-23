package com.catpaw.catpawmiddleware.service.member;


import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.exception.custom.UnauthorizedException;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import com.catpaw.catpawcore.utils.LogUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public long checkAndGetMemberId(Optional<Long> idHolder) {
        return idHolder.orElseThrow(() -> {
            throw new UnauthorizedException("로그인하지 않은 사용자입니다.");
        });
    }

    public Member getReferenceMember(Long memberId) {
        Assert.notNull(memberId, LogUtils.notNullFormat("memberId"));

        return memberRepository.getReferenceById(memberId);
    }
}
