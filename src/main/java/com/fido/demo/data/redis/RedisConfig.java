package com.fido.demo.data.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fido.demo.controller.service.pojo.SessionState;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;


@Configuration
class RedisConfig {


    @Bean
    public RedisTemplate<String, SessionState> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, SessionState> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // Use Jackson2JsonRedisSerializer for serialization
        Jackson2JsonRedisSerializer<SessionState> serializer = new Jackson2JsonRedisSerializer<>(SessionState.class);
        ObjectMapper objectMapper = new ObjectMapper();
        // Customize the ObjectMapper if needed
        serializer.setObjectMapper(objectMapper);


        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(serializer);
        return template;
    }


}
