package com.vt.hacks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppEvents {
	
	private static final Logger log = LoggerFactory.getLogger(AppEvents.class);

    @Autowired
    ApplicationContext applicationContext;

    @EventListener(ApplicationReadyEvent.class)
    public void startApp() {        
        log.debug("Schedule Job Service Ready");
    }
}
