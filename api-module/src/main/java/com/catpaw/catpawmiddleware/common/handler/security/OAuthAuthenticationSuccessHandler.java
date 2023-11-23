package com.catpaw.catpawmiddleware.common.handler.security;

import com.catpaw.catpawcore.domain.security.MemberContextsImpl;
import com.catpaw.catpawcore.domain.security.MemberContext;
import com.catpaw.catpawcore.utils.URLUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.log.LogMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class OAuthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private static final String REDIRECT_URI = "/oauth/success";
    private final JwtTokenManager jwtTokenManager;
    private final String redirectUrl;

    public OAuthAuthenticationSuccessHandler(JwtTokenManager jwtTokenManager, String frontUrl) {
        this.jwtTokenManager = jwtTokenManager;
        this.redirectUrl = frontUrl + REDIRECT_URI;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        MemberContextsImpl memberContextsImpl = (MemberContextsImpl) authentication.getPrincipal();
        MemberContext member = memberContextsImpl.getMemberContext();

        String token = jwtTokenManager.createToken(member.getId(), member.getEmail(), authentication.getAuthorities());

        String targetUrl = this.getTargetUrl(token);

        if (response.isCommitted()) {
            this.logger.debug(LogMessage.format("Did not redirect to %s since response already committed.", targetUrl));
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String getTargetUrl(String token) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("token", token);
        return URLUtils.createUrl(this.redirectUrl, queryParams);
    }

}
