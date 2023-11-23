package com.catpaw.catpawmiddleware.common.handler.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomAuthorityMapper implements GrantedAuthoritiesMapper {

    private static final String OAUTH_USER = "OAUTH2_USER";

    @Override
    public Set<GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        HashSet<GrantedAuthority> mapped = new HashSet<>(authorities.size());
        for (GrantedAuthority authority : authorities) {
            GrantedAuthority grantedAuthority = convertAuthority(authority.getAuthority());
            if (grantedAuthority != null) mapped.add(grantedAuthority);
        }
        return mapped;
    }

    private GrantedAuthority convertAuthority(String name) {
        if (OAUTH_USER.equals(name)) {
            return new SimpleGrantedAuthority(name);
        }
        return null;
    }
}
