package com.catpaw.catpawmiddleware.common.resolver;

import com.catpaw.catpawmiddleware.common.resolver.annotation.LoginId;
import com.catpaw.catpawcore.domain.security.MemberContextsImpl;
import com.catpaw.catpawcore.exception.custom.MemberNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Slf4j
public final class LoginIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginId.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal == null || principal instanceof String) {
            return Optional.empty();
        }

        try {
            MemberContextsImpl context = (MemberContextsImpl) principal;

            return Optional.ofNullable(context.getMemberContext().getId());
        } catch (Exception e) {
            throw new MemberNotFoundException("로그인 되지 않은 사용자입니다.");
        }
    }
}
