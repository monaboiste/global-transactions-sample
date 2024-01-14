package com.github.monaboiste.transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;

@Slf4j
@RequiredArgsConstructor
class SpringEventBus implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
       log.info("Publishing event [{}]", event.eventId());

       applicationEventPublisher.publishEvent(event);

       log.info("Event [{}] published", event.eventId());
    }
}
