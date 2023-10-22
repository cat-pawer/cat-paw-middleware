package com.catpaw.catpawmiddleware.common.converter;

import com.catpaw.catpawmiddleware.domain.model.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;


public interface MemberPrincipalConverter {

    boolean supports(MemberAuthenticationFormFactory form);

    MemberContext converter(MemberAuthenticationFormFactory form);
}
