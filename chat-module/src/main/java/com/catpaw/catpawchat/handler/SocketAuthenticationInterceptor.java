package com.catpaw.catpawchat.handler;

import com.catpaw.catpawcore.common.handler.security.JwtTokenManager;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SocketAuthenticationInterceptor implements HandshakeInterceptor {

    @Autowired
    JwtTokenManager jwtTokenManager;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, @NonNull ServerHttpResponse response, @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) throws Exception {
        String query = request.getURI().getQuery();
        if (!StringUtils.hasText(query)) {
            throw new AuthenticationException("인증 토큰이 존재하지 않습니다");
        }

        try {
            String[] tokenQuery = query.split("&");
            String tokenValue = (tokenQuery[0].split("="))[1];
            boolean result = jwtTokenManager.validateToken(tokenValue);
            return true;
        }
        catch (Exception e) {
            throw new AuthenticationException("잘못된 쿼리 파라미터 정보입니다");
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
