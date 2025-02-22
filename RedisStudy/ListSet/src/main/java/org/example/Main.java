package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");

        /*  Redis와 통신하기 위해 Jedis 연결을 관리하는 Pool */
        // 풀링 기법 -> 연결을 미리 생성하고 재사용하여 성능 최적화
        // try 사용 -> 풀 close 자동화
        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (Jedis jedis = jedisPool.getResource()) {

                /* LIST */
                // 1. stack : Last In First Out
                jedis.rpush("stack1", "aaaa");
                jedis.rpush("stack1", "bbbb");
                jedis.rpush("stack1", "cccc");

//                List<String> stack1 = jedis.lrange("stack1", 0, -1);
//                stack1.forEach(System.out::println);

                System.out.println(jedis.rpop("stack1"));
                System.out.println(jedis.rpop("stack1"));
                System.out.println(jedis.rpop("stack1"));

                // 2. queue : 순차적처리, First In First Out
                jedis.rpush("queue1", "zzzz");
                jedis.rpush("queue1", "aaaa");
                jedis.rpush("queue1", "cccc");

                System.out.println(jedis.lpop("queue1"));
                System.out.println(jedis.lpop("queue1"));
                System.out.println(jedis.lpop("queue1"));

                // 3. block : BLPOP, BRPOP
                List<String> blpop = jedis.blpop(10, "queue:blocking");

                if (blpop != null) {
                    blpop.forEach(System.out::println);
                }

                /* SET */
                jedis.sadd("users:500:follow", "100", "200", "300");
                jedis.srem("users:500:follow", "100");

                Set<String> smembers = jedis.smembers("users:500:follow");
                smembers.forEach(System.out::println);

                System.out.println(jedis.sismember("users:500:follow", "200"));
                System.out.println(jedis.sismember("users:500:follow", "120"));

                System.out.println(jedis.scard("users:500:follow"));

                Set<String> sinter = jedis.sinter("users:500:follow", "users:100:follow");
                sinter.forEach(System.out::println);

            }
        }
    }
}