package com.github.monaboiste.transactional.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class ApplicationDomainEventPublisher<T extends Snapshot> implements DomainEventPublisher<T> {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(final BatchDomainEvent<T> events) {
        log.info("Publishing events {}", events);
        applicationEventPublisher.publishEvent(events);
    }
}
