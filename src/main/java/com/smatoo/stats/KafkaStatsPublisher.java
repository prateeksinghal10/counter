package com.smatoo.stats;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaStatsPublisher implements StatsPublisher {

    private Logger LOG = LoggerFactory.getLogger(KafkaStatsPublisher.class);

    private String topicName = "request-stats";

    private KafkaTemplate<String, Long> kafkaTemplate;

    public KafkaStatsPublisher(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String hourKey, Long numberOfRequest) {
        kafkaTemplate.send(topicName, hourKey, numberOfRequest);
        LOG.info("Unique Records{} in minute {} published to topic{}", numberOfRequest, hourKey, topicName);
    }
}
