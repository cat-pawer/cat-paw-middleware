package com.catpaw.catpawchat.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final RedisTemplate<String, Object> redisTemplate;

    public ChatService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(Topic topic, MessageDto messageDto) {
        redisTemplate.convertAndSend(topic.getDestination(), messageDto);
    }
}
