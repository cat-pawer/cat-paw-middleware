package com.catpaw.catpawmiddleware.common.converter;

import com.catpaw.catpawmiddleware.domain.model.MemberContext;
import com.catpaw.catpawmiddleware.common.factory.authentication.MemberAuthenticationFormFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class DelegatingMemberContextConverter {

    private final List<MemberContextConverter> converterList;

    public DelegatingMemberContextConverter() {
        List<MemberContextConverter> converters = Arrays.asList(
                new LocalMemberContextConverter(),
                new GoogleMemberContextConverter(),
                new NaverMemberContextConverter()
        );

        this.converterList = Collections.unmodifiableList(new LinkedList<>(converters));
    }

    public MemberContext convert(MemberAuthenticationFormFactory form) {
          for (MemberContextConverter converter : converterList) {
            if (!converter.supports(form)) {
                continue;
            }

            return converter.converter(form);
        }

        return null;
    }
}
