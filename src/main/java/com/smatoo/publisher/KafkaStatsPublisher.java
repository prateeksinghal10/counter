package com.smatoo.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaStatsPublisher implements StatsPublisher {

    private Logger LOG = LoggerFactory.getLogger(KafkaStatsPublisher.class);

    @Value("${topic.name}")
    private String topicName;

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
