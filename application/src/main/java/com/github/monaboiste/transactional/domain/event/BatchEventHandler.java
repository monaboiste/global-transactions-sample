package com.github.monaboiste.transactional.domain.event;

/**
 * Internal usage only.
 */
public interface BatchEventHandler extends EventHandler<DomainEvent> {

    void process(BatchDomainEvent events);
}
