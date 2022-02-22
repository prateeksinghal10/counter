package com.smatoo.counter;

import com.smatoo.stats.StatsPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class CounterDistributed implements Counter {
    private static Logger LOG = LoggerFactory.getLogger(CounterDistributed.class);

    private Jedis jedis;

    private StatsPublisher statsPublisher;

    public CounterDistributed(Jedis jedis, StatsPublisher statsPublisher) {
        this.jedis = jedis;
        this.statsPublisher = statsPublisher;
    }

    @Override
    public void addCount(int id) {

        jedis.sadd(KEY_FORMAT.format(LocalDateTime.now()), String.valueOf(id));

    }

    @Override
    public Long getUniqueCountForCurrentMinute() {
        String key = KEY_FORMAT.format(LocalDateTime.now());
        return jedis.scard(key);
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void print() {
        String key = KEY_FORMAT.format(LocalDateTime.now().minus(1, ChronoUnit.MINUTES));
        statsPublisher.publish(key, jedis.scard(key));
    }
}
