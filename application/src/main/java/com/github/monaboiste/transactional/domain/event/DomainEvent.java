package com.github.monaboiste.transactional.domain.event;

/**
 * Represents internal (pure) domain application event.
 */
public interface DomainEvent extends Event {

    String aggregateType();

    String aggregateId();

    /**
     * @return the class of this event
     */
    Class<? extends Event> type();
}
