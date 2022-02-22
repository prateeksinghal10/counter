package com.smatoo.publisher;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "publisher", havingValue = "logger")
public class LogPublisherConfig {

    @Bean
    public StatsPublisher statsPublisher(){
        return new LoggerStatsPublisher();
    }


}
