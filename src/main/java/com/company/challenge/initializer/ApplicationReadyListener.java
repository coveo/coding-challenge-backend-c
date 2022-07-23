package com.company.challenge.initializer;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("init")
public class ApplicationReadyListener {

    private final CityInitializer cityInitializer;

    @EventListener(ApplicationReadyEvent.class)
    public void initialize() {
        cityInitializer.initialize();
    }
}
