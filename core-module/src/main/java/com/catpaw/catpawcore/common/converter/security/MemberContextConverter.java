package com.catpaw.catpawcore.common.converter.security;

import com.catpaw.catpawcore.domain.security.MemberContext;
import com.catpaw.catpawcore.common.factory.authentication.MemberAuthenticationFormFactory;


public interface MemberContextConverter {

    boolean supports(MemberAuthenticationFormFactory form);

    MemberContext converter(MemberAuthenticationFormFactory form);
}
