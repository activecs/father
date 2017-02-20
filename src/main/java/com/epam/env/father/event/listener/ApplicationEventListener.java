package com.epam.env.father.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.epam.env.father.event.ReleasedEnvironmentEvent;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ApplicationEventListener {

    @EventListener
    public void onApplicationEvent(ReleasedEnvironmentEvent event) {
        log.info(() -> "Got event" + event);
    }

}
