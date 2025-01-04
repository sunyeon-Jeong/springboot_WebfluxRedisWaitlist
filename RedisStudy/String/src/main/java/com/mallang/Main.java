package com.mallang;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        /* Redis와 통신하기 위해 Jedis 연결을 관리하는 pool */
        // 풀링 기법 -> 연결을 미리 생성하고 재사용하여 성능 최적화
        // try 사용 -> 풀 close 자동화
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                System.out.println("Redis 연결 상태: " + jedis.ping());

                jedis.set("users:300:name", "mallang");
                jedis.set("users:300:email", "mallang@mallang.com");
                jedis.set("users:300:age", "23");

                var userEmail = jedis.get("users:300:email");
                System.out.println(userEmail);

                List<String> userInfo = jedis.mget("users:300:name", "users:300:email", "users:300:age");
                userInfo.forEach(System.out::println);

                long counter = jedis.incr("counter");
                System.out.println(counter);

                counter = jedis.incrBy("counter", 10L);
                System.out.println(counter);

                counter = jedis.decr("counter");
                System.out.println(counter);

                counter = jedis.decrBy("counter", 20L);
                System.out.println(counter);

                // Jedis Pipeline 사용 -> 대량의 데이터 set 성능 최적화
                Pipeline pipelined = jedis.pipelined();
                pipelined.set("users:400:name", "chunsik");
                pipelined.set("users:400:email", "chunsik@chunsik.com");
                pipelined.set("users:400:age", "24");

                List<Object> objects = pipelined.syncAndReturnAll();
                objects.forEach(i -> System.out.println(i.toString()));

            }
        }

    }
}