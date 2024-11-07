package com.github.monaboiste.transactional.domain.event;

/**
 * Represents internal (pure) domain application event.
 */
public interface DomainEvent<T extends Snapshot> extends Event<T> {

    String aggregateType();

    String aggregateId();

    /**
     * @return the class of this event
     */
    Class<? extends Event<T>> type();
}
