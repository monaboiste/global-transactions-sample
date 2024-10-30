package com.github.monaboiste.transactional.domain.event;

import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface Event extends Serializable {

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
     * @param <T> type of the payload
     */
    @Nullable
    <T extends Serializable> T payload();
}
