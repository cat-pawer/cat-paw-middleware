package com.catpaw.catpawchat.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;


public class ActionSubscribe implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ActionSubscribe.class);

    private final ObjectMapper objectMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public ActionSubscribe(SimpMessagingTemplate simpMessagingTemplate) {
        this.objectMapper = new ObjectMapper();
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        simpMessagingTemplate.convertAndSend("/topic/room", message);
    }
}
