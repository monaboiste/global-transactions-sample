package com.github.monaboiste.transactional.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class InternalEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void publish(final Event event) {
        log.info("Publishing event: %s".formatted(event));
        applicationEventPublisher.publishEvent(event);
    }
}
