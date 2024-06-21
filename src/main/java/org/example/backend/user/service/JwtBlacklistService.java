package org.example.backend.user.service;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    public JwtBlacklistService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
    }

    public void addToBlacklist(String token, long durationInSeconds) {
        valueOperations.set(token, "blacklisted", durationInSeconds, TimeUnit.SECONDS);
    }

    public boolean isBlacklisted(String token) {
        return valueOperations.get(token) != null;
    }
}
