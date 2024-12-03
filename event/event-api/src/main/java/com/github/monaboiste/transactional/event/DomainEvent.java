package com.github.monaboiste.transactional.event;

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
