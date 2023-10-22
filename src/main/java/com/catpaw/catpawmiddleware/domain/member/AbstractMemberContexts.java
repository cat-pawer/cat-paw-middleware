package com.catpaw.catpawmiddleware.domain.member;

import com.catpaw.catpawmiddleware.domain.model.MemberContext;
import com.catpaw.catpawmiddleware.domain.eumns.SocialType;
import com.catpaw.catpawmiddleware.utils.UuidUtils;
import jakarta.annotation.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class AbstractMemberContexts implements MemberContexts {

    private MemberContext member;

    protected final Map<String, Object> attributes;

    protected List<? extends GrantedAuthority> authorities;

    public AbstractMemberContexts(MemberContext member, Collection<? extends GrantedAuthority> authorities, @Nullable Map<String, Object> attributes) {
        Assert.notNull(member, "member cannot be null");
        Assert.notNull(authorities, "authorities cannot be null");

        this.member = member;
        this.authorities = authorities.stream().toList();
        this.attributes = attributes;
    }

    @Override
    public MemberContext getMemberContext() {
        return this.member;
    }

    @Override
    public void updateMember(MemberContext memberDto) {
        this.member = memberDto;
    }

    @Override
    public String getPassword() { return UuidUtils.createUuid(); }

    @Override
    public Long getId() {
        return member.getId();
    }

    @Override
    public String getUsername() {
        return this.member.getName();
    }

    @Override
    public String getEmail() {
        return member.getEmail();
    }

    @Override
    public SocialType getSocialType() {
        return member.getSocialType();
    }

    abstract public Map<String, Object> getAttributes();

    abstract public List<? extends GrantedAuthority> getAuthorities();
}
