package com.smatoo.counter;

import com.smatoo.counter.Counter;
import com.smatoo.counter.CounterDistributed;
import com.smatoo.counter.CounterLocal;
import com.smatoo.stats.StatsPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableScheduling
public class CounterConfig {

    @Bean
    public Counter counterLocal(StatsPublisher statsPublisher) {
        return new CounterLocal(statsPublisher);
    }

   /* @Bean
    public Jedis jedis() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(5);

        Jedis jedis = new Jedis(new HostAndPort("127.0.0.1", 6379));
        jedis.connect();
        return jedis;
    }

    @Bean
    public Counter counterDistributed(Jedis jedis, StatsPublisher statsPublisher) {
        return new CounterDistributed(jedis, statsPublisher);
    }*/
}