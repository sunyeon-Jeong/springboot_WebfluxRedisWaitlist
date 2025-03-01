package com.mallang.springbootcache;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RequiredArgsConstructor // final, @notnull 필드 값만 파라미터로 받는 생성자 자동생성
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final JedisPool jedisPool;

    @GetMapping("/users/{id}/email")
    public String getUserEmail(@PathVariable Long id) {
        try (Jedis jedis = jedisPool.getResource()) {
            // request id 값 -> redis key 값 설정
            var userEmailRedisKey = "users:%d:email".formatted(id);

            // 분산서버(Redis)에서 1차조회
            String userEmail = jedis.get(userEmailRedisKey);

            // 분산서버(Redis) 조회값 반환
            if (userEmail != null) {
                return userEmail;
            }

            // DB에서 2차조회
            userEmail = userRepository.findById(id).orElse(User.builder().build()).getEmail();

            // Redis TTL 설정
            jedis.setex(userEmailRedisKey, 30, userEmail);

            return userEmail;
        }

    }
}