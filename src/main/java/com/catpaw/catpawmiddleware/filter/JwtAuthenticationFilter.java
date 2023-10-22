package com.catpaw.catpawmiddleware.filter;

import com.catpaw.catpawmiddleware.common.handler.security.JwtTokenManager;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

public class JwtAuthenticationFilter implements Filter {

    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenManager jwtTokenManager, UserDetailsService userDetailsService) {
        this.jwtTokenManager = jwtTokenManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenManager.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenManager.validateToken(token)) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenManager.getUserId(token));
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
