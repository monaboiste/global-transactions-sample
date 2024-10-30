package com.github.monaboiste.transactional.domain.event;

import java.util.List;

public interface DomainEventStore {

    <T extends Snapshot> void save(DomainEvent<T> event);

    <T extends Snapshot> void saveAll(List<DomainEvent<T>> events);
}
