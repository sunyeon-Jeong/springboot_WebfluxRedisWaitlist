package com.mallang.springrediscache.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mallang.springrediscache.domain.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    // User 객체를 직렬화 (Jackson2JsonRedisSerializer 방식)
    @Bean
    RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory connectionFactory) {

        var objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) // 알 수 없는 속성이 있어도 예외발생X
                .registerModule(new JavaTimeModule()) // LocalDateTime 날짜관련객체 직렬화
                .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS); // 날짜를 timestamp 대신 문자열로 저장

        var template = new RedisTemplate<String, User>();
        template.setConnectionFactory(connectionFactory); // key를 문자열로 직렬화
        template.setKeySerializer(new StringRedisSerializer()); // User 객체를 JSON으로 직렬화
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapper, User.class));

        return template;

    }

}

/*
1. Object Mapper와 Redis Template의 관계
- Object Mapper는 Java객체를 JSON으로 직렬화하거나 역직렬화하는 역할을 함
- Redis Template는 Redis와 데이터를 주고 받을 때 직렬화/역직렬화를 수행하는 도구
-> Redis Template는 객체를 Redis에 저장하고 가져올 때 Object Mapper를 이용해 수행
 */

/*
2. Redis Template가 직렬화를 필요로 하는 이유
- Redis는 단순한 데이터 타입을 지원함
-> Java의 복잡한 객체(dto, entity 등)를 Redis에 그대로 저장할 수 없음
 */