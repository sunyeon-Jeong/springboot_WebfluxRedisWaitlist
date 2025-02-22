package com.mallang;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        /*  Redis와 통신하기 위해 Jedis 연결을 관리하는 Pool */
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                /*
                데이터를 단건으로 저장 할 때
                hset(String key, String field, String value)

                데이터를 다건으로 저장 할 때 -> Map 형식 사용
                hset(String key, Map<String, String> hash)
                */

                // hset 값 저장
                jedis.hset("users:1:info", "name", "mallang");

                var userInfo = new HashMap<String, String>();
                userInfo.put("email", "mallang@mallang.com");
                userInfo.put("phone", "010-0000-0000");

                jedis.hset("users:2:info", userInfo);

                // hdel 값 삭제
                jedis.hdel("users:2:info", "phone");

                // hget, hgetall
                System.out.println(jedis.hget("users:2:info", "email"));
                Map<String, String> user2Info = jedis.hgetAll("users:2:info");
                user2Info.forEach((k, v) -> System.out.printf("%s %s%n", k, v));

                // hincr 숫자 값 증가
                jedis.hincrBy("users:2:info", "visits", 30);

            }
        }

    }
}