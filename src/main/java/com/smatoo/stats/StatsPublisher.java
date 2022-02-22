package com.smatoo.stats;

public interface StatsPublisher {

    public void publish(String hourKey, Long numberOfRequest);
}
