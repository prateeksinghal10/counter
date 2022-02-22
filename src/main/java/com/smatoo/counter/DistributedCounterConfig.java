package com.smatoo.counter;

import com.smatoo.publisher.StatsPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "counter", havingValue = "distributed")
public class DistributedCounterConfig {

    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private int redisPort;


    @Bean
    public Jedis jedis() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(5);

        Jedis jedis = new Jedis(new HostAndPort(redisHost, redisPort));
        jedis.connect();
        return jedis;
    }

    @Bean
    public Counter counterDistributed(Jedis jedis, StatsPublisher statsPublisher) {
        return new DistributedCounter(jedis, statsPublisher);
    }
}
