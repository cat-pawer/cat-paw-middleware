package com.catpaw.catpawcore.common.handler.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
public class JwtTokenManager {

    @Value("${token-valid-time}")
    private long tokenValidTime;

    @Value("${secret-key}")
    private String secretKey;

    private Key key;

    private static final String BEARER = "Bearer ";

    private static final String TOKEN_KEY = "Authorization";

    public JwtTokenManager() {}

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secretKey));
    }

    public String createToken(Long memberId, String email, Collection<? extends GrantedAuthority> roles) {

        Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
        claims.put("email", email);
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserId(String token) {
        return Jwts.
                parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_KEY);
        if (token != null && token.startsWith(BEARER)) {
            return token.substring(token.lastIndexOf(BEARER) + BEARER.length());
        }

        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts
                    .parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
