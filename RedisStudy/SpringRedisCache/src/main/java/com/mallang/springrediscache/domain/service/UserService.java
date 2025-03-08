package com.mallang.springrediscache.domain.service;

import com.mallang.springrediscache.domain.entity.User;
import com.mallang.springrediscache.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RedisTemplate<String, User> userRedisTemplate;

    /* Cache-Aside Pattern */
    public User getUser(final Long id) {

        var key = "users:%d".formatted(id);

        // Redis에서 get
        var cachedUser = userRedisTemplate.opsForValue().get(key);
        if (cachedUser != null) {
            return cachedUser;
        }

        // DB에서 get
        User dbGetUser = userRepository.findById(id).orElseThrow();
        // DB에서 get 한 값 -> Redis에 set
        userRedisTemplate.opsForValue().set(key, dbGetUser);
        return dbGetUser;

    }

}