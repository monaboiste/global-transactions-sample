package com.github.monaboiste.transactional.domain.event;

/**
 * Internal usage only.
 */
public interface BatchEventHandler {

    void process(BatchDomainEvent<?> events);
}
