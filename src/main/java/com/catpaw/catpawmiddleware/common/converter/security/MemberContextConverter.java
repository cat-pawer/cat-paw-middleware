package com.catpaw.catpawmiddleware.common.converter.security;

import com.catpaw.catpawmiddleware.domain.security.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;


public interface MemberContextConverter {

    boolean supports(MemberAuthenticationFormFactory form);

    MemberContext converter(MemberAuthenticationFormFactory form);
}
