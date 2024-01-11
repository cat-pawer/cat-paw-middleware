package com.catpaw.catpawmiddleware.controller.v1.member;

import com.catpaw.catpawcore.common.handler.security.JwtTokenManager;
import com.catpaw.catpawcore.domain.entity.Member;
import com.catpaw.catpawcore.domain.eumns.ResponseCode;
import com.catpaw.catpawmiddleware.controller.v1.response.Result;
import com.catpaw.catpawcore.repository.member.MemberRepository;
import com.catpaw.catpawcore.service.member.MemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "회원", description = "회원 도메인 API")
@SecurityRequirement(name = "bearer-token")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtTokenManager jwtTokenManager;

    @GetMapping("/token/{memberId}")
    public ResponseEntity<Result<String>> testToken(@PathVariable Long memberId) {
        Optional<Member> byId = memberRepository.findById(memberId == null ? 1L : memberId);
        Member testMember = byId.get();

        String token = jwtTokenManager.createToken(testMember.getId(), testMember.getEmail(), List.of(new SimpleGrantedAuthority("member")));

        return ResponseEntity
                .ok()
                .body(Result.createSingleResult(ResponseCode.SUCCESS.getCode(), null, token));
    }
}
