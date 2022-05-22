package com.company.goodreadsapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public final class CacheUtil {
    private final RedissonClient redissonClient;
    private final ObjectMapper mapper;

    @SneakyThrows
    public <T> void set(String key, T data) {
        var bucket = redissonClient.getBucket(key);
        var serialized = mapper.writeValueAsString(data);
        bucket.set(serialized);
    }

    @SneakyThrows
    public <T> void set(String key, T data, int timeToLive, TimeUnit timeUnit) {
        var bucket = redissonClient.getBucket(key);
        var serialized = mapper.writeValueAsString(data);
        bucket.set(serialized, timeToLive, timeUnit);
    }

    @SneakyThrows
    public <T> T get(String key) {
        var bucket = redissonClient.getBucket(key);
        return bucket.isExists() ? mapper.readValue((String) bucket.get(), new TypeReference<T>() {
        }) : null;
    }

    public void delete(String key) {
        var bucket = redissonClient.getBucket(key);
        if (bucket.isExists()) {
            bucket.delete();
        }
    }

    public static String createKey(String... values) {
        var joiner = new StringJoiner(":");
        for (String value : values) {
            joiner.add(value);
        }
        return joiner.toString();
    }
}
