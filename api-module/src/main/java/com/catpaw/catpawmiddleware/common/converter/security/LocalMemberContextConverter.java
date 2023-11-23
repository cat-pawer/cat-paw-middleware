package com.catpaw.catpawmiddleware.common.converter.security;

import com.catpaw.catpawcore.domain.security.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;

public class LocalMemberContextConverter implements MemberContextConverter {

    @Override
    public boolean supports(MemberAuthenticationFormFactory form) {
        return form.getClientRegistration() == null;
    }

    @Override
    public MemberContext converter(MemberAuthenticationFormFactory form) {
        return MemberContext.fromEntity(form.getMember());
    }
}
