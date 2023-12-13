package com.haoever.hack.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleRunner {
    private static final Logger log =
        LoggerFactory.getLogger(ScheduleRunner.class);


    // cron expression
    @Scheduled(cron = "* * * * * ?")
//    @Scheduled(fixedRate = 3000)
    public void NotifyStockPrice() throws IOException {
        log.debug("[ScheduleRunner] : {}", LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:SS")));
    }
}
