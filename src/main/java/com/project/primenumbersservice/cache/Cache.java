package com.project.primenumbersservice.cache;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Cache<K, V> {

    public static final Long DEFAULT_CACHE_TIMEOUT = 300000L;

    protected Map<K, CacheValue<V>> cacheMap;
    protected Long cacheTimeout;

    public Cache() {
        this(DEFAULT_CACHE_TIMEOUT);
    }

    public Cache(Long cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
        this.clear();
    }

    public void clean() {
        for (K key: this.getExpiredKeys()) {
            this.remove(key);
        }
    }

    public Map<K, CacheValue<V>> getCleanedCacheMap() {
        this.clean();
        return cacheMap;
    }

    public boolean containsKey(K key) {
        return this.cacheMap.containsKey(key);
    }

    protected Set<K> getExpiredKeys() {
        return this.cacheMap.keySet().parallelStream()
                .filter(this::isExpired)
                .collect(Collectors.toSet());
    }

    protected boolean isExpired(K key) {
        LocalDateTime expirationDateTime = this.cacheMap.get(key).getCreatedAt().plus(this.cacheTimeout, ChronoUnit.MILLIS);
        return LocalDateTime.now().isAfter(expirationDateTime);
    }

    public void clear() {
        this.cacheMap = new HashMap<>();
    }

    public Optional<V> get(K key) {
        return Optional.ofNullable(this.cacheMap.get(key)).map(CacheValue::getValue);
    }

    public void put(K key, V value) {
        this.cacheMap.put(key, this.createCacheValue(value));
    }

    protected CacheValue<V> createCacheValue(V value) {
        LocalDateTime now = LocalDateTime.now();
        return new CacheValue<V>() {
            @Override
            public V getValue() {
                return value;
            }
            @Override
            public LocalDateTime getCreatedAt() {
                return now;
            }
        };
    }

    public void remove(K key) {
        this.cacheMap.remove(key);
    }

    protected interface CacheValue<V> {
        V getValue();
        LocalDateTime getCreatedAt();
    }
}
