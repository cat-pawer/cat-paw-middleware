package com.catpaw.catpawmiddleware.domain.member;

import com.catpaw.catpawmiddleware.domain.model.MemberContext;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

public interface MemberContexts {

    MemberContext getMemberContext();

    void updateMember(MemberContext memberDto);

    Long getId();

    String getUsername();

    String getPassword();

    String getEmail();

    SocialType getSocialType();

    Map<String, Object> getAttributes();

    List<? extends GrantedAuthority> getAuthorities();
}
