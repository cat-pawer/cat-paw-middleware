package com.catpaw.catpawmiddleware.controller.v1.member;

import com.catpaw.catpawcore.common.handler.security.JwtTokenManager;
import com.catpaw.catpawcore.common.resolver.annotation.LoginId;
import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.eumns.ResponseCode;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawmiddleware.repository.member.MemberRepository;
import com.catpaw.catpawmiddleware.service.member.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenManager jwtTokenManager;

    @GetMapping("/token")
    public String testToken() {
        Optional<Member> byId = memberRepository.findById(1L);
        Member testMember = byId.get();
        return jwtTokenManager.createToken(testMember.getId(), testMember.getEmail(), List.of(new SimpleGrantedAuthority("member")));
    }


}
