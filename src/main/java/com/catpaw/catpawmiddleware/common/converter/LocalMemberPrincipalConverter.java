package com.catpaw.catpawmiddleware.common.converter;

import com.catpaw.catpawmiddleware.domain.model.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;

public class LocalMemberPrincipalConverter implements MemberPrincipalConverter {

    @Override
    public boolean supports(MemberAuthenticationFormFactory form) {
        return form.getClientRegistration() == null;
    }

    @Override
    public MemberContext converter(MemberAuthenticationFormFactory form) {
        return MemberContext.fromEntity(form.getMember());
    }
}
