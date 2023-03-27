package com.fc.final7.global.redis;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RedisProperties {

    private String redisHost;
    private int redisPort;



    @Value("${redis.host}")
    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }
    @Value("${redis.port}")
    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }
}
