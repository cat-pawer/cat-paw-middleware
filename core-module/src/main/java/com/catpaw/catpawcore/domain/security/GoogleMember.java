package com.catpaw.catpawcore.domain.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class GoogleMember extends AbstractMemberContexts {

    public GoogleMember(MemberContext member, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        super(member, authorities, attributes);
    }

    @Override
    public Map<String, Object> getAttributes() { return this.attributes; }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
