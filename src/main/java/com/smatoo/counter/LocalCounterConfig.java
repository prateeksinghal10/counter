package com.smatoo.counter;

import com.smatoo.publisher.StatsPublisher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "counter",havingValue = "local")
public class LocalCounterConfig {

    @Bean
    public Counter counterLocal(StatsPublisher statsPublisher) {
        return new LocalCounter(statsPublisher);
    }
}