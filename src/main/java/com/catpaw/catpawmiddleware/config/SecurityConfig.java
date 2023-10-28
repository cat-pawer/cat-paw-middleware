package com.catpaw.catpawmiddleware.config;


import com.catpaw.catpawmiddleware.common.handler.security.CustomAuthorityMapper;
import com.catpaw.catpawmiddleware.common.handler.security.OAuthAuthenticationSuccessHandler;
import com.catpaw.catpawmiddleware.filter.JwtAuthenticationFilter;
import com.catpaw.catpawmiddleware.common.handler.security.JwtTokenManager;
import com.catpaw.catpawmiddleware.service.security.SecurityLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    SecurityLoginService securityLoginService;

    @Autowired
    JwtTokenManager jwtTokenManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/**").permitAll();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(conf ->
                        conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2Login(conf -> {
            conf.redirectionEndpoint(redirectConf -> redirectConf.baseUri("/oauth/code/**"));
            conf.userInfoEndpoint(
                    userConf -> {
                        userConf
//                                .oidcUserService(customOidcUserService)
                                .userService(securityLoginService);
                    }
            );
            conf.successHandler(oAuthAuthenticationSuccessHandler());
        });
        http.addFilterBefore(
                new JwtAuthenticationFilter(jwtTokenManager, userDetailsService),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }

    @Bean
    public OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler() {
        return new OAuthAuthenticationSuccessHandler(jwtTokenManager);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/error");
    }
}
