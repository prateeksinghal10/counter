package com.smatoo.counter;

import com.smatoo.publisher.StatsPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ConcurrentHashMap;

public class LocalCounter implements Counter {

    private static Logger LOG= LoggerFactory.getLogger(LocalCounter.class);

    private ConcurrentHashMap<String, ConcurrentHashMap.KeySetView<Integer, Boolean>> counterCache = new ConcurrentHashMap<>();

    private StatsPublisher statsPublisher;

    public LocalCounter(StatsPublisher statsPublisher) {
        this.statsPublisher = statsPublisher;
    }

    @Override
    public void addCount(int id) {
        String cacheKey = KEY_FORMAT.format(LocalDateTime.now());
        ConcurrentHashMap.KeySetView<Integer, Boolean> requests = counterCache.get(cacheKey);
        if (requests == null) {
            requests = ConcurrentHashMap.<Integer>newKeySet();
            ConcurrentHashMap.KeySetView<Integer, Boolean> oldValue = counterCache.putIfAbsent(cacheKey, requests);
            if (oldValue != null) {
                requests = oldValue;
            }
            counterCache.put(cacheKey, requests);
        }
        requests.add(id);
    }

    @Override
    public Long getUniqueCountForCurrentMinute() {
        String key = KEY_FORMAT.format(LocalDateTime.now());
        return Long.valueOf(counterCache.getOrDefault(key, ConcurrentHashMap.<Integer>newKeySet()).size());
    }

    @Scheduled(cron = "0 */1 * * * *")
    @Override
    public void print() {
        String key = KEY_FORMAT.format(LocalDateTime.now().minus(1, ChronoUnit.MINUTES));
        statsPublisher.publish(key,counterCache.getOrDefault(key, ConcurrentHashMap.<Integer>newKeySet()).stream().count());
    }
}
