package com.mallang;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoSearchParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");

        try (var jedisPool = new JedisPool("127.0.0.1", 6379)) {
            try (var jedis = jedisPool.getResource()) {

                // geo add
                jedis.geoadd("store:geo", 127.02985530619755, 37.49911212874, "some1");
                jedis.geoadd("store:geo", 127.0333352287619, 37.491921163986234, "some2");

                // geo dist
                Double geodist = jedis.geodist("store:geo", "some1", "some2");
                System.out.println(geodist);

                // geo search
                List<GeoRadiusResponse> radiusResponseList = jedis.geosearch (
                        "store:geo"
                        , new GeoCoordinate(127.033, 37.495) // 기준좌표
                        , 500 // 반경 500m
                        , GeoUnit.M // 단위 m
                );

                // geo search (GeoSearchParam 활용)
                List<GeoRadiusResponse> radiusResponseList1 = jedis.geosearch (
                        "store:geo"
                        , new GeoSearchParam ()
                                .fromLonLat(new GeoCoordinate(127.033, 37.495)) // 기준좌표
                                .byRadius(500, GeoUnit.M) // 반경 500 단위 m
                                .withCoord() // 검색 결과에 좌표정보 포함
                );

                radiusResponseList1.forEach (response ->
                                System.out.println("%s %f %f".formatted(
                                        response.getMemberByString()
                                        , response.getCoordinate().getLatitude()
                                        , response.getCoordinate().getLongitude()
                                ))
                        );

                // geo 삭제
                jedis.unlink("stores:geo");

            }
        }

    }
}