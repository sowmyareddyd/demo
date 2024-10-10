package com.fido.demo.data.redis;

import com.fido.demo.controller.service.pojo.SessionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, SessionState> redisTemplate;

    // Save JSON data with a custom key
    public void save(String key, SessionState data) {
        redisTemplate.opsForValue().set(key, data);
    }

    // Retrieve JSON data by key
    public SessionState find(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Delete a key
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
