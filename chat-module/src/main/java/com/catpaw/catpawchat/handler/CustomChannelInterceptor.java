package com.catpaw.catpawchat.handler;

import com.catpaw.catpawchat.controller.v1.request.MessageDto;
import com.catpaw.catpawchat.controller.v1.request.MessageType;
import com.catpaw.catpawchat.service.MessageService;
import com.catpaw.catpawchat.service.RedisService;
import com.catpaw.catpawcore.common.handler.security.EncryptManager;
import com.catpaw.catpawcore.common.handler.security.JwtTokenManager;
import com.catpaw.catpawcore.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Component
public class CustomChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenManager jwtTokenManager;
    private final EncryptManager encryptManager;
    private final RedisService redisService;

    public CustomChannelInterceptor(JwtTokenManager jwtTokenManager, EncryptManager encryptManager, @Lazy RedisService redisService) {
        this.jwtTokenManager = jwtTokenManager;
        this.encryptManager = encryptManager;
        this.redisService = redisService;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand command = accessor.getCommand();

        if (StompCommand.CONNECT.equals(command)) {
            String token = accessor.getFirstNativeHeader("token");
            String dest = accessor.getFirstNativeHeader("dest");
            if (token != null && dest != null) {
                String userId = jwtTokenManager.getUserId(token);
                String authToken = userId + "|" + dest + "|" + LocalDateTime.now();
                String encrypt = encryptManager.encrypt(authToken);
                StompPrincipal stompPrincipal = new StompPrincipal(encrypt);
                accessor.setUser(stompPrincipal);
            }

            return message;
        }

        else if (StompCommand.DISCONNECT.equals(command)) {
            if (accessor.getUser() != null) {
                Map<String, Object> tokenMap = resolveToken(accessor.getUser().getName());
                Long memberId = (Long) tokenMap.get("memberId");
                Long groupId = (Long) tokenMap.get("groupId");
                Assert.notNull(memberId, LogUtils.notNullFormat("memberId"));
                Assert.notNull(groupId, LogUtils.notNullFormat("groupId"));

                redisService.deleteHashData("group:" + groupId, "member:" + memberId);

                MessageDto<Void, Void> responseDto = new MessageDto<>();
                responseDto.setMemberId(memberId);
                responseDto.setGroupId(groupId);
                responseDto.setMessageType(MessageType.LEAVE);

                redisService.broadcast("/result", responseDto);
            }
        }

        else {
            validAuthorizationToken(accessor.getFirstNativeHeader("Authorization"));
        }
        return message;
    }

    public void validAuthorizationToken(String token) {
        Assert.hasText(token, LogUtils.notEmptyFormat(token));
        String plainToken = encryptManager.decrypt(token);

        String[] split = plainToken.split("\\|");
        try {
            if (split.length == 3) {
                Long.parseLong(split[0]);
                Long.parseLong(split[1]);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Map<String, Object> resolveToken(String token) {
        Assert.hasText(token, LogUtils.notEmptyFormat(token));

        String plainToken = encryptManager.decrypt(token);
        String[] split = plainToken.split("\\|");

        Map<String, Object> result = new HashMap<>();

        try {
            if (split.length == 3) {
                result.put("memberId", Long.parseLong(split[0]));
                result.put("groupId", Long.parseLong(split[1]));

                return result;
            }
            throw new IllegalArgumentException();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
