package com.github.monaboiste.transactional.domain.event;

import java.util.List;
import java.util.function.Consumer;

public record BatchDomainEvent<T extends Snapshot>(List<DomainEvent<T>> events) {

    public void forEach(Consumer<DomainEvent<T>> action) {
        events.forEach(action);
    }

    public List<DomainEvent<T>> domainEvents() {
        return events;
    }
}
