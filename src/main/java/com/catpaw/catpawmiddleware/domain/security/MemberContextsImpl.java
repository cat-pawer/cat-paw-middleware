package com.catpaw.catpawmiddleware.domain.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public record MemberContextsImpl(MemberContexts memberContexts) implements UserDetails, OAuth2User {

    public MemberContext getMemberContext() { return memberContexts.getMemberContext(); }

    @Override
    public String getName() {
        return memberContexts.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return memberContexts.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return memberContexts.getAuthorities();
    }

    @Override
    public String getPassword() {
        return memberContexts.getPassword();
    }

    @Override
    public String getUsername() {
        return memberContexts.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
