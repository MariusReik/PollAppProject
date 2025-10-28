package no.hvl.pollapp.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PollCache {

    private final RedisTemplate<String, Object> redisTemplate;

    public PollCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Cacheable(value = "polls", key = "#key")
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
