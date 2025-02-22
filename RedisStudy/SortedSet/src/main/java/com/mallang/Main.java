package com.mallang;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.resps.Tuple;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (var jedis = jedisPool.getResource()) {

                // sorted set
                var scores = new HashMap<String, Double>();

                scores.put("users1", 100.0);
                scores.put("users2", 30.0);
                scores.put("users3", 50.0);
                scores.put("users4", 80.0);
                scores.put("users5", 15.0);

                // zadd  값추가
                jedis.zadd("game:scores", scores);

                // zrange 값조회 (asc)
                List<String> zrange = jedis.zrange("game:scores", 0, Long.MAX_VALUE);
                zrange.forEach(System.out::println);

                // zcard 전체 개수 확인
                System.out.println(jedis.zcard("game:scores"));

                // zrange with scores 점수와 함께 출력
                List<Tuple> tuples = jedis.zrangeWithScores("game:scores", 0, Long.MAX_VALUE);
                tuples.forEach(i -> System.out.println("%s %f".formatted(i.getElement(), i.getScore())));

            }
        }

    }
}