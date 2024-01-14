package com.github.monaboiste.transactional;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public interface DomainEvent extends Serializable {
    String name();
    UUID eventId();
    Instant createdAt();
}
