package com.smatoo.publisher;

public interface StatsPublisher {

    public void publish(String hourKey, Long numberOfRequest);
}
