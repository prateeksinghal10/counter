package com.smatoo.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerStatsPublisher implements StatsPublisher {

    private static Logger LOG = LoggerFactory.getLogger(LoggerStatsPublisher.class);

    @Override
    public void publish(String hourKey, Long numberOfRequest) {
        LOG.info("Unique Records in minute {} is {}", hourKey, numberOfRequest);
    }
}
