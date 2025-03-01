package com.mallang.springbootcache;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

// 재사용이 가능한 독립된 모듈
@Component // Spring Bean으로 등록 (의존성 주입 DI)
public class RedisConfig {

    @Bean
    public JedisPool createJedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setJmxEnabled(false); // JMX 기능 비활성화
        return new JedisPool(poolConfig, "127.0.0.1", 6379);
    }

}