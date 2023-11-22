package com.catpaw.catpawmiddleware.domain.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LocalMember extends AbstractMemberContexts {

    public LocalMember(MemberContext member, Collection<? extends GrantedAuthority> authorities) {
        super(member, authorities, null);
    }

    @Override
    public Map<String, Object> getAttributes() { return this.attributes; }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
