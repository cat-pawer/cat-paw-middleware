package com.catpaw.catpawmiddleware.config;


import com.catpaw.catpawcore.common.handler.security.CustomAuthorityMapper;
import com.catpaw.catpawcore.common.handler.security.OAuthAuthenticationSuccessHandler;
import com.catpaw.catpawmiddleware.filter.JwtAuthenticationFilter;
import com.catpaw.catpawcore.common.handler.security.JwtTokenManager;
import com.catpaw.catpawmiddleware.service.security.SecurityLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${front-url}")
    private String frontUrl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/api/v1/swagger-ui/**").permitAll()
                            .requestMatchers("/api/v1/**").permitAll()
                            .anyRequest().permitAll();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(conf ->
                        conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2Login(conf -> {
            conf
                    .redirectionEndpoint(
                            redirectConf ->
                                    redirectConf.baseUri("/api/v1/oauth/code/**"))
                    .userInfoEndpoint(
                            userConf ->
                                    userConf.userService(securityLoginService)
                    )
                    .authorizationEndpoint(
                            authConf ->
                                    authConf.baseUri("/api/v1/oauth/authorization")
                    )
                    .successHandler(oAuthAuthenticationSuccessHandler());
        });
        http.addFilterBefore(
                new JwtAuthenticationFilter(jwtTokenManager(), userDetailsService),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtTokenManager jwtTokenManager() {
        return new JwtTokenManager();
    }

    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }

    @Bean
    public OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler() {
        return new OAuthAuthenticationSuccessHandler(jwtTokenManager(), this.frontUrl);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/error");
    }
}
