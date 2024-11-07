package com.github.monaboiste.transactional.domain.event;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface Event<T extends Snapshot> extends Serializable {

    /**
     * @return event identifier
     */
    UUID eventId();

    /**
     * @return the name of the event
     */
    String name();

    /**
     * @return the precise moment we first observed the event
     */
    Instant occurredAt();

    /**
     * @return a serializable payload
     */
    T payload();
}
