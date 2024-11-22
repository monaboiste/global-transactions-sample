package com.github.monaboiste.transactional.event.bus.spring;

import com.github.monaboiste.transactional.event.BatchDomainEvent;
import com.github.monaboiste.transactional.event.DomainEventPublisher;
import com.github.monaboiste.transactional.event.Snapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
@RequiredArgsConstructor
public class ApplicationDomainEventPublisher<T extends Snapshot> implements DomainEventPublisher<T> {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(final BatchDomainEvent<T> events) {
        log.info("Publishing events {}", events);
        applicationEventPublisher.publishEvent(events);
    }
}
