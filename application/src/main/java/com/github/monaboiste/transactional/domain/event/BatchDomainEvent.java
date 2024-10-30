package com.github.monaboiste.transactional.domain.event;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public final class BatchDomainEvent {

    private final List<DomainEvent> events;

    public void forEach(Consumer<DomainEvent> action) {
        events.forEach(action);
    }

    public List<DomainEvent> domainEvents() {
        return events;
    }
}
