package com.github.monaboiste.transactional.domain.employee;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface Event extends Serializable {

    default String name() {
        return getClass().getSimpleName();
    }

    Class<? extends Event> type();

    UUID eventId();

    Instant occurredAt();
}
