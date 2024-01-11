package com.catpaw.catpawchat.service;

import com.catpaw.catpawchat.controller.v1.request.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() {
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public void broadcast(String destination, MessageDto<?, ?> messageDto) {
        redisTemplate.convertAndSend(destination, messageDto);
    }

    public <T> Optional<T> getHashData(String key, String hashKey, Class<T> clazz) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String data = hashOperations.get(key, hashKey);

        try {
            return Optional.of(objectMapper.readValue(data, clazz));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void putHashData(String key, String hashKey, Object data) {
        try {
            HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
            String strValue = objectMapper.writeValueAsString(data);
            hashOperations.put(key, hashKey, strValue);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void putIfAbsentHashData(String key, String hashKey, Object data) {
        try {
            HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
            String strValue = objectMapper.writeValueAsString(data);
            hashOperations.put(key, hashKey, strValue);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteHashData(String key, String hashKey) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hashKey);
    }

    public <T> Map<String, T> getHashEntries(String key, Class<T> clazz) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        try {
            Map<String, String> entries = hashOperations.entries(key);
            Map<String, T> mapper = new HashMap<>();
            for (Map.Entry<String, String> entry : entries.entrySet()) {
                mapper.put(entry.getKey(), objectMapper.readValue(entry.getValue(), clazz));
            }
            return mapper;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
