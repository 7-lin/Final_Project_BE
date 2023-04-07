package com.fc.final7.global.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Getter
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;


    public void setValuesWithTimeout(String key, String token, Long validTime, TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, token, validTime, timeUnit);
    }
    public String getValues(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Transactional
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }


}
