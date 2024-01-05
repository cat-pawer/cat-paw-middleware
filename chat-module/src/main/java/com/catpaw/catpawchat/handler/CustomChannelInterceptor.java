package com.catpaw.catpawchat.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.security.Principal;


@Slf4j
@Component
public class CustomChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        StompCommand command = accessor.getCommand();

        Principal user = accessor.getUser();

        if (StompCommand.CONNECT.equals(command)) {
            return message;
        }

        if (StompCommand.SUBSCRIBE.equals(command)) {
            return message;
        }


        else if (command == StompCommand.SEND) {
            return message;
        }

        return message;
    }
}
