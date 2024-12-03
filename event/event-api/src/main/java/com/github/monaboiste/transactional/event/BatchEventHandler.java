package com.github.monaboiste.transactional.event;

/**
 * Internal usage only.
 */
public interface BatchEventHandler {

    void process(BatchDomainEvent<?> events);
}
