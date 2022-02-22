package com.smatoo.counter;

import org.springframework.scheduling.annotation.Scheduled;

import java.time.format.DateTimeFormatter;

public interface Counter {
    DateTimeFormatter KEY_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy-hh-mm");

    void addCount(int id);

    Long getUniqueCountForCurrentMinute();

    @Scheduled(cron = "0 */1 * * * *")
    void print();
}
