package com.github.monaboiste.transactional.domain.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class ApplicationDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(final BatchDomainEvent events) {
        log.info("Publishing events {}", events);
        applicationEventPublisher.publishEvent(events);
    }
}
