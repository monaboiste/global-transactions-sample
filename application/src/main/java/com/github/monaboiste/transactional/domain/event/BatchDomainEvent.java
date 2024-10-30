package com.github.monaboiste.transactional.domain.event;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public final class BatchDomainEvent<T extends Snapshot> {

    private final List<DomainEvent<T>> events;

    public void forEach(Consumer<DomainEvent<T>> action) {
        events.forEach(action);
    }

    public List<DomainEvent<T>> domainEvents() {
        return events;
    }
}
