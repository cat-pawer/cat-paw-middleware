package com.catpaw.catpawchat.handler;

import com.catpaw.catpawchat.controller.v1.request.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;


@Slf4j
public class MessageSubscriber implements MessageListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SimpMessagingTemplate simpMessagingTemplate;

    public MessageSubscriber(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {

        try {
            MessageDto<?, ?> messageDto = objectMapper.readValue(message.getBody(), MessageDto.class);

            simpMessagingTemplate.convertAndSend("/topic/" + messageDto.getGroupId(), messageDto);
        } catch (IOException e) {
            log.error("onMessage failed = {}", e.getMessage());
        }

    }
}
