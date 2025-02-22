package com.mallang;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (var jedis = jedisPool.getResource()) {

                jedis.setbit("request-somepage-20250301", 100, true);
                jedis.setbit("request-somepage-20250301", 200, true);
                jedis.setbit("request-somepage-20250301", 300, true);

                System.out.println(jedis.getbit("request-somepage-20250301", 100));
                System.out.println(jedis.getbit("request-somepage-20250301", 50));

                System.out.println(jedis.bitcount("request-somepage-20250301"));

                // bitmap vs set (메모리사용률 비교)
                Pipeline pipelined = jedis.pipelined(); // 여러개의 redis 명령어를 한번에 전송
                IntStream.rangeClosed(0, 100000).forEach(i -> {
                   pipelined.sadd("request-somepage-set-20250302", String.valueOf(i), "1");
                   pipelined.setbit("request-somepage-bit-20250302", 1, true);

                   if (1 == 1000) {
                       pipelined.sync();
                   }
                });

                pipelined.sync();

            }
        }

    }
}